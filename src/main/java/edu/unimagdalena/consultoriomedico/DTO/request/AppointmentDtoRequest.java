package edu.unimagdalena.consultoriomedico.DTO.request;

import edu.unimagdalena.consultoriomedico.enumaration.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentDtoRequest(
        Long idPatient,
        Long idDoctor,
        Long idConsultRoom,
        LocalDateTime startTime,
        LocalDateTime endTime,
        AppointmentStatus status
) {
}
