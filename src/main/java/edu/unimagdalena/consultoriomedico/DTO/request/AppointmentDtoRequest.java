package edu.unimagdalena.consultoriomedico.DTO.request;

import java.time.LocalDateTime;

public record AppointmentDtoRequest(
        Long idPatient,
        Long idDoctor,
        Long idConsultRoom,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status
) {
}
