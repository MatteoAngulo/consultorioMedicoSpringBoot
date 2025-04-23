package edu.unimagdalena.consultoriomedico.repositories;

import edu.unimagdalena.consultoriomedico.entities.Appointment;
import edu.unimagdalena.consultoriomedico.entities.ConsultRoom;
import edu.unimagdalena.consultoriomedico.entities.Doctor;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class AppointmentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository     patientRepository;
    @Autowired
    private DoctorRepository      doctorRepository;
    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    private Patient    paciente;
    private Doctor     doctor;
    private ConsultRoom consultorio;


    @BeforeEach
    void setUp() {
        paciente = patientRepository.save(
                Patient.builder()
                        .fullName("Paciente Test")
                        .email("paciente@test.com")
                        .phone("3001234567")
                        .build()
        );

        doctor = doctorRepository.save(
                Doctor.builder()
                        .fullName("Dr. House")
                        .email("house@princeton.com")
                        .speciality("Diagnóstico")
                        .availableFrom(LocalTime.of(8, 0))
                        .availableTo(LocalTime.of(16, 0))
                        .build()
        );

        consultorio = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consultorio 1")
                        .floor("3")
                        .description("Sala de diagnóstico")
                        .build()
        );
    }

    @Test
    void shouldDetectConsultRoomConflict() {
        LocalDateTime inicio1 = LocalDate.now().plusDays(1).atTime(10, 0);
        LocalDateTime fin1    = inicio1.plusHours(1);

        appointmentRepository.save(
                Appointment.builder()
                        .patient(paciente)
                        .doctor(doctor)
                        .consultRoom(consultorio)
                        .startTime(inicio1)
                        .endTime(fin1)
                        .status("AGENDADO")
                        .build()
        );

        List<Appointment> conflictos = appointmentRepository.findConsultRoomConflicts(
                consultorio.getIdConsultRoom(),
                inicio1.plusMinutes(30),
                fin1.plusMinutes(30)
        );
        assertFalse(conflictos.isEmpty(), "Debe detectar conflicto en el mismo ConsultRoom");
    }

    @Test
    void shouldDetectDoctorConflict() {
        // 2do consultorio para la cita conflictiva
        ConsultRoom consultorio2 = consultRoomRepository.save(
                ConsultRoom.builder()
                        .name("Consultorio 2")
                        .floor("4")
                        .description("Sala adicional")
                        .build()
        );

        LocalDateTime s1 = LocalDate.now().plusDays(1).atTime(9, 0);
        LocalDateTime e1 = s1.plusHours(1);

        appointmentRepository.save(
                Appointment.builder()
                        .patient(paciente)
                        .doctor(doctor)
                        .consultRoom(consultorio)
                        .startTime(s1)
                        .endTime(e1)
                        .status("AGENDADO")
                        .build()
        );

        List<Appointment> conflictos = appointmentRepository.findDoctorConflicts(
                doctor.getIdDoctor(),
                s1.plusMinutes(30),
                e1.plusMinutes(30)
        );
        assertFalse(conflictos.isEmpty(), "Debe detectar conflicto para el mismo Doctor");
    }

    @Test
    void shouldSaveAppointmentWithinDoctorAvailability() {
        LocalDateTime s = LocalDate.now().plusDays(2).atTime(11, 0);
        LocalDateTime e = s.plusHours(1);

        Appointment a = appointmentRepository.save(
                Appointment.builder()
                        .patient(paciente)
                        .doctor(doctor)
                        .consultRoom(consultorio)
                        .startTime(s)
                        .endTime(e)
                        .status("AGENDADO")
                        .build()
        );

        assertNotNull(a.getIdAppointment());
        assertEquals(s, a.getStartTime());
        assertEquals(e, a.getEndTime());
    }

    @Test
    void shouldNotAllowAppointmentInPast() {
        LocalDateTime pasado = LocalDateTime.now().minusHours(1);

        assertThrows(ConstraintViolationException.class, () -> {
            appointmentRepository.saveAndFlush(
                    Appointment.builder()
                            .patient(paciente)
                            .doctor(doctor)
                            .consultRoom(consultorio)
                            .startTime(pasado)
                            .endTime(pasado.plusHours(1))
                            .status("AGENDADO")
                            .build()
            );
        }, "No se debe permitir agendar en el pasado (@Future)");
    }

    @Test
    void shouldNotAllowOutsideDoctorAvailability() {
        LocalDateTime early = LocalDate.now().plusDays(1).atTime(7, 0);
        LocalDateTime late  = early.plusHours(1);

        assertThrows(IllegalArgumentException.class, () -> {
            if (early.toLocalTime().isBefore(doctor.getAvailableFrom()) ||
                    late.toLocalTime().isAfter(doctor.getAvailableTo())) {
                throw new IllegalArgumentException("Fuera del horario del doctor");
            }
            appointmentRepository.save(
                    Appointment.builder()
                            .patient(paciente)
                            .doctor(doctor)
                            .consultRoom(consultorio)
                            .startTime(early)
                            .endTime(late)
                            .status("AGENDADO")
                            .build()
            );
        }, "No debe agendar fuera del horario disponible del doctor");
    }

    @Test
    void shouldListAllAppointments() {
        LocalDateTime t1 = LocalDate.now().plusDays(4).atTime(9, 0);
        LocalDateTime t2 = LocalDate.now().plusDays(4).atTime(11, 0);

        Appointment a1 = appointmentRepository.save(
                Appointment.builder()
                        .patient(paciente)
                        .doctor(doctor)
                        .consultRoom(consultorio)
                        .startTime(t1)
                        .endTime(t1.plusHours(1))
                        .status("AGENDADO")
                        .build()
        );

        Appointment a2 = appointmentRepository.save(
                Appointment.builder()
                        .patient(paciente)
                        .doctor(doctor)
                        .consultRoom(consultorio)
                        .startTime(t2)
                        .endTime(t2.plusHours(1))
                        .status("AGENDADO")
                        .build()
        );

        List<Appointment> todas = appointmentRepository.findAll();
        assertTrue(todas.contains(a1) && todas.contains(a2));
        assertEquals(2, todas.size());
    }

    @Test
    void shouldUpdateAppointment() {
        LocalDateTime s = LocalDate.now().plusDays(5).atTime(14, 0);
        LocalDateTime e = s.plusHours(1);

        Appointment original = appointmentRepository.save(
                Appointment.builder()
                        .patient(paciente)
                        .doctor(doctor)
                        .consultRoom(consultorio)
                        .startTime(s)
                        .endTime(e)
                        .status("AGENDADO")
                        .build()
        );

        original.setStatus("COMPLETADO");
        Appointment updated = appointmentRepository.save(original);

        Appointment fetched = appointmentRepository.findById(updated.getIdAppointment()).orElseThrow();
        assertEquals("COMPLETADO", fetched.getStatus());
    }

    @Test
    void shouldDeleteAndFindById() {
        LocalDateTime s = LocalDate.now().plusDays(3).atTime(12, 0);
        LocalDateTime e = s.plusHours(1);

        Appointment saved = appointmentRepository.save(
                Appointment.builder()
                        .patient(paciente)
                        .doctor(doctor)
                        .consultRoom(consultorio)
                        .startTime(s)
                        .endTime(e)
                        .status("AGENDADO")
                        .build()
        );

        Appointment fetched = appointmentRepository.findById(saved.getIdAppointment()).orElseThrow();
        assertEquals(saved.getIdAppointment(), fetched.getIdAppointment());

        appointmentRepository.deleteById(saved.getIdAppointment());
        assertFalse(appointmentRepository.findById(saved.getIdAppointment()).isPresent());
    }
}
