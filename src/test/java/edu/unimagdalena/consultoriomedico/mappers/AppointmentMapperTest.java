package edu.unimagdalena.consultoriomedico.mappers;

import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoUpdateRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.AppointmentDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Appointment;
import edu.unimagdalena.consultoriomedico.entities.ConsultRoom;
import edu.unimagdalena.consultoriomedico.entities.Doctor;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import edu.unimagdalena.consultoriomedico.enumaration.AppointmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentMapperTest {

    private AppointmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AppointmentMapperImpl();
    }

    @Test
    void testToAppointmentDtoResponse() {

        Patient patient = Patient.builder()
                .idPatient(5L)
                .build();

        Doctor doctor = Doctor.builder()
                .idDoctor(6L)
                .availableFrom(null)
                .availableTo(null)
                .build();

        ConsultRoom room = ConsultRoom.builder()
                .idConsultRoom(7L)
                .build();

        LocalDateTime start = LocalDateTime.now().plusDays(1).plusHours(9);
        LocalDateTime end   = start.plusHours(1);

        Appointment appointment = Appointment.builder()
                .idAppointment(10L)
                .patient(patient)
                .doctor(doctor)
                .consultRoom(room)
                .startTime(start)
                .endTime(end)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        AppointmentDtoResponse dto = mapper.toAppointmentDtoResponse(appointment);

        assertThat(dto.idAppointment()).isEqualTo(10L);
        assertThat(dto.idPatient()).isEqualTo(5L);
        assertThat(dto.idDoctor()).isEqualTo(6L);
        assertThat(dto.idConsultRoom()).isEqualTo(7L);
        assertThat(dto.startTime()).isEqualTo(start);
        assertThat(dto.endTime()).isEqualTo(end);
        assertThat(dto.status()).isEqualTo("SCHEDULED");
    }

    @Test
    void testToEntity() {
        LocalDateTime start = LocalDateTime.now().plusDays(2).plusHours(9);
        LocalDateTime end   = start.plusHours(2);
        AppointmentDtoRequest dtoRequest = new AppointmentDtoRequest(
                5L, 6L, 7L, start, end
        );

        Appointment entity = mapper.toEntity(dtoRequest);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getStartTime()).isEqualTo(start);
        assertThat(entity.getEndTime()).isEqualTo(end);
        // Mapper ignores associations and id
        assertThat(entity.getIdAppointment()).isNull();
        assertThat(entity.getPatient()).isNull();
        assertThat(entity.getDoctor()).isNull();
        assertThat(entity.getConsultRoom()).isNull();
        assertThat(entity.getStatus()).isNull();
    }

    @Test
    void testUpdateAppointmentFromDto() {
        LocalDateTime originalStart = LocalDateTime.now().plusDays(3).plusHours(9);
        LocalDateTime originalEnd   = originalStart.plusHours(1);
        Appointment appointment = Appointment.builder()
                .idAppointment(20L)
                .startTime(originalStart)
                .endTime(originalEnd)
                .status(AppointmentStatus.SCHEDULED)
                .build();

        LocalDateTime newStart = LocalDateTime.now().plusDays(4).plusHours(9);
        LocalDateTime newEnd   = newStart.plusHours(1);
        AppointmentDtoUpdateRequest updateDto = new AppointmentDtoUpdateRequest(
                newStart, newEnd, AppointmentStatus.CANCELED
        );

        mapper.updateAppointmentFromDto(updateDto, appointment);

        assertThat(appointment.getStartTime()).isEqualTo(newStart);
        assertThat(appointment.getEndTime()).isEqualTo(newEnd);
        assertThat(appointment.getStatus()).isEqualTo(AppointmentStatus.CANCELED);
    }
}
