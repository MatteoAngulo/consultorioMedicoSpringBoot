package edu.unimagdalena.consultoriomedico.repositories;

import edu.unimagdalena.consultoriomedico.entities.Appointment;
import edu.unimagdalena.consultoriomedico.entities.ConsultRoom;
import edu.unimagdalena.consultoriomedico.entities.Doctor;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.LocalTime;

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


    @Test
    void shouldSaveAndFindAppointment(){
        Patient patient = Patient.builder()
                .idPatient(1L)
                .fullName("Juan Pérez")
                .email("juan.perez@example.com")
                .phone("+573001234567")
                .build();

        // Crear Doctor
        Doctor doctor = Doctor.builder()
                .idDoctor(1L)
                .fullName("Dra. María González")
                .email("maria.gonzalez@clinica.com")
                .speciality("Cardiología")
                .availableFrom(LocalTime.of(8, 0))
                .availableTo(LocalTime.of(17, 0))
                .build();

        // Crear ConsultRoom
        ConsultRoom consultRoom = ConsultRoom.builder()
                .idConsultRoom(101L)
                .name("Consulta 101")
                .floor("1")
                .description("Consultorio de Cardiología")
                .build();

        // Crear Appointment
        Appointment appointment = Appointment.builder()
                .idAppointment(1L)
                .patient(patient)
                .doctor(doctor)
                .consultRoom(consultRoom)
                .startTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0))
                .endTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30))
                .status("Programado")
                .build();
    }
}
