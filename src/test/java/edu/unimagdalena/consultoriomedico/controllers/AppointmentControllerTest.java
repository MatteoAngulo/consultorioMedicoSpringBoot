package edu.unimagdalena.consultoriomedico.controllers;

import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoUpdateRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.AppointmentDtoResponse;
import edu.unimagdalena.consultoriomedico.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;
    @InjectMocks
    private AppointmentController appointmentController;

    private AppointmentDtoResponse sampleDto;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start = LocalDateTime.of(2025, 5, 10, 9, 0);
        end   = start.plusHours(1);
        sampleDto = new AppointmentDtoResponse(1L, 2L, 3L, 4L, start, end, "SCHEDULED");
    }

    @Test
    void getAllAppointments_returnsOkAndList() {
        when(appointmentService.findAllAppointments()).thenReturn(List.of(sampleDto));

        ResponseEntity<List<AppointmentDtoResponse>> response = appointmentController.getAllAppointments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleDto, response.getBody().get(0));
        verify(appointmentService).findAllAppointments();
    }

    @Test
    void getAppointmentById_returnsOkAndDto() {
        when(appointmentService.findAppointmentById(1L)).thenReturn(sampleDto);

        ResponseEntity<AppointmentDtoResponse> response = appointmentController.getAppointmentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleDto, response.getBody());
        verify(appointmentService).findAppointmentById(1L);
    }

    @Test
    void createAppointment_returnsCreatedAndDto() {
        AppointmentDtoRequest req = new AppointmentDtoRequest(2L,3L,4L, start, end);
        when(appointmentService.saveAppointment(any(AppointmentDtoRequest.class))).thenReturn(sampleDto);

        ResponseEntity<AppointmentDtoResponse> response = appointmentController.createAppointment(req);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleDto, response.getBody());
        verify(appointmentService).saveAppointment(req);
    }

    @Test
    void updateAppointment_returnsOkAndDto() {
        AppointmentDtoUpdateRequest updateReq = new AppointmentDtoUpdateRequest(start, end, null);
        when(appointmentService.updateAppointment(eq(1L), any(AppointmentDtoUpdateRequest.class))).thenReturn(sampleDto);

        ResponseEntity<AppointmentDtoResponse> response = appointmentController.updateAppointment(1L, updateReq);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleDto, response.getBody());
        verify(appointmentService).updateAppointment(1L, updateReq);
    }

    @Test
    void deleteAppointment_returnsNoContent() {
        doNothing().when(appointmentService).deleteAppointment(1L);

        ResponseEntity<Void> response = appointmentController.deleteAppointment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(appointmentService).deleteAppointment(1L);
    }
}
