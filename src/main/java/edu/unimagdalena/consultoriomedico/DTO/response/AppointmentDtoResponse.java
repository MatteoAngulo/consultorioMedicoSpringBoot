package edu.unimagdalena.consultoriomedico.DTO.response;

import java.time.LocalDateTime;

public record AppointmentDtoResponse(
        Long idAppointment,
        Long idPatient,
        Long idDoctor,
        Long idConsultRoom,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status
) {
}
