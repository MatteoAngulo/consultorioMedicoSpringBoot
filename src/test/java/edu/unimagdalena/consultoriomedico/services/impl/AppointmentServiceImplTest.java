package edu.unimagdalena.consultoriomedico.services.impl;

import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoUpdateRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.AppointmentDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Appointment;
import edu.unimagdalena.consultoriomedico.entities.ConsultRoom;
import edu.unimagdalena.consultoriomedico.entities.Doctor;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import edu.unimagdalena.consultoriomedico.enumaration.AppointmentStatus;
import edu.unimagdalena.consultoriomedico.exceptions.*;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.*;
import edu.unimagdalena.consultoriomedico.mappers.AppointmentMapper;
import edu.unimagdalena.consultoriomedico.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private ConsultRoomRepository consultRoomRepository;
    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl service;

    private Patient patient;
    private Doctor doctor;
    private ConsultRoom consultRoom;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        patient = Patient.builder().idPatient(1L).build();
        doctor = Doctor.builder()
                .idDoctor(2L)
                .availableFrom(LocalTime.of(8,0))
                .availableTo(LocalTime.of(17,0))
                .build();
        consultRoom = ConsultRoom.builder().idConsultRoom(3L).build();

        LocalDateTime now = LocalDateTime.now();
        start = now.plusDays(1).withHour(9).withMinute(0).withSecond(0);
        end   = start.plusHours(1);
    }

    @Test
    void findAllAppointments_shouldReturnMappedResponses() {
        Appointment appt = Appointment.builder().idAppointment(10L).build();
        when(appointmentRepository.findAll()).thenReturn(List.of(appt));
        AppointmentDtoResponse dto = new AppointmentDtoResponse(10L,1L,2L,3L,start,end,"SCHEDULED");
        when(appointmentMapper.toAppointmentDtoResponse(appt)).thenReturn(dto);

        List<AppointmentDtoResponse> result = service.findAllAppointments();

        assertThat(result).hasSize(1).contains(dto);
        verify(appointmentRepository).findAll();
        verify(appointmentMapper).toAppointmentDtoResponse(appt);
    }

    @Test
    void findAppointmentById_whenExists_returnsDto() {
        Appointment appt = Appointment.builder().idAppointment(11L).build();
        when(appointmentRepository.findById(11L)).thenReturn(Optional.of(appt));
        AppointmentDtoResponse dto = new AppointmentDtoResponse(11L,1L,2L,3L,start,end,"SCHEDULED");
        when(appointmentMapper.toAppointmentDtoResponse(appt)).thenReturn(dto);

        AppointmentDtoResponse result = service.findAppointmentById(11L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void findAppointmentById_whenNotFound_throwsException() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(AppointmentNotFoundException.class, () -> service.findAppointmentById(99L));
    }

    @Test
    void saveAppointment_whenValid_returnsDto() {
        AppointmentDtoRequest req = new AppointmentDtoRequest(1L,2L,3L,start,end);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(consultRoomRepository.findById(3L)).thenReturn(Optional.of(consultRoom));
        when(appointmentRepository.findConflicts(3L,start,end)).thenReturn(List.of());
        when(appointmentRepository.findConflicsWithDoctor(2L,start,end)).thenReturn(List.of());
        Appointment entity = Appointment.builder().startTime(start).endTime(end).build();
        when(appointmentMapper.toEntity(req)).thenReturn(entity);
        Appointment saved = Appointment.builder().idAppointment(20L).build();
        when(appointmentRepository.save(entity)).thenReturn(saved);
        AppointmentDtoResponse resp = new AppointmentDtoResponse(20L,1L,2L,3L,start,end,"SCHEDULED");
        when(appointmentMapper.toAppointmentDtoResponse(saved)).thenReturn(resp);

        AppointmentDtoResponse result = service.saveAppointment(req);

        assertThat(result).isEqualTo(resp);
    }

    @Test
    void saveAppointment_whenInvalidTime_throwsInvalidTimeRange() {
        LocalDateTime badStart = start.plusHours(2);
        AppointmentDtoRequest req = new AppointmentDtoRequest(1L,2L,3L,badStart,start);
        assertThrows(InvalidTimeRangeException.class, () -> service.saveAppointment(req));
    }

    @Test
    void saveAppointment_whenPatientNotFound_throwsPatientNotFound() {
        AppointmentDtoRequest req = new AppointmentDtoRequest(1L,2L,3L,start,end);
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> service.saveAppointment(req));
    }

    @Test
    void deleteAppointment_whenNotExists_throwsException() {
        when(appointmentRepository.existsById(50L)).thenReturn(false);
        assertThrows(AppointmentNotFoundException.class, () -> service.deleteAppointment(50L));
    }

    @Test
    void deleteAppointment_whenExists_deletes() {
        when(appointmentRepository.existsById(30L)).thenReturn(true);
        service.deleteAppointment(30L);
        verify(appointmentRepository).deleteById(30L);
    }

    @Test
    void updateAppointment_whenCompleted_throwsAppointmentAlreadyCompletedException() {
        Appointment existing = Appointment.builder()
                .idAppointment(10L)
                .status(AppointmentStatus.COMPLETED)
                .startTime(start.plusHours(2))
                .endTime(end.plusHours(2))
                .build();
        when(appointmentRepository.findById(10L)).thenReturn(Optional.of(existing));

        AppointmentDtoUpdateRequest dto = new AppointmentDtoUpdateRequest(
                start.plusHours(3), end.plusHours(3), AppointmentStatus.CANCELED
        );

        assertThrows(AppointmentAlreadyCompletedException.class,
                () -> service.updateAppointment(10L, dto));
    }

    @Test
    void updateAppointment_whenPastStartTime_throwsAppointmentInThePastException() {
        Appointment existing = Appointment.builder()
                .idAppointment(11L)
                .status(AppointmentStatus.SCHEDULED)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(LocalDateTime.now().minusDays(1).plusHours(1))
                .build();
        when(appointmentRepository.findById(11L)).thenReturn(Optional.of(existing));

        AppointmentDtoUpdateRequest dto = new AppointmentDtoUpdateRequest(
                start, end, AppointmentStatus.SCHEDULED
        );

        assertThrows(AppointmentInThePastException.class,
                () -> service.updateAppointment(11L, dto));
    }

    @Test
    void updateAppointment_whenInvalidTimeRange_throwsInvalidTimeRangeException() {
        Appointment existing = Appointment.builder()
                .idAppointment(12L)
                .status(AppointmentStatus.SCHEDULED)
                .startTime(start.plusHours(5))
                .endTime(start.plusHours(6))
                .build();
        when(appointmentRepository.findById(12L)).thenReturn(Optional.of(existing));

        // invalid: new start equal or after new end
        LocalDateTime invalidStart = start.plusHours(2);
        AppointmentDtoUpdateRequest dto = new AppointmentDtoUpdateRequest(
                invalidStart, invalidStart, AppointmentStatus.SCHEDULED
        );

        assertThrows(InvalidTimeRangeException.class,
                () -> service.updateAppointment(12L, dto));
    }

    @Test
    void updateAppointment_whenValid_updatesAndReturnsDto() {
        Appointment existing = Appointment.builder()
                .idAppointment(13L)
                .status(AppointmentStatus.SCHEDULED)
                .startTime(start.plusHours(4))
                .endTime(start.plusHours(5))
                .build();
        when(appointmentRepository.findById(13L)).thenReturn(Optional.of(existing));

        LocalDateTime newStart = start.plusHours(6);
        LocalDateTime newEnd   = newStart.plusHours(1);
        AppointmentDtoUpdateRequest dto = new AppointmentDtoUpdateRequest(
                newStart, newEnd, AppointmentStatus.CANCELED
        );
        doNothing().when(appointmentMapper).updateAppointmentFromDto(dto, existing);
        Appointment saved = Appointment.builder().idAppointment(13L).build();
        when(appointmentRepository.save(existing)).thenReturn(saved);
        AppointmentDtoResponse response = new AppointmentDtoResponse(
                13L, 1L, 2L, 3L, newStart, newEnd, "CANCELED"
        );
        when(appointmentMapper.toAppointmentDtoResponse(saved)).thenReturn(response);

        AppointmentDtoResponse result = service.updateAppointment(13L, dto);

        assertThat(result).isEqualTo(response);
        verify(appointmentMapper).updateAppointmentFromDto(dto, existing);
        verify(appointmentRepository).save(existing);
    }
}
