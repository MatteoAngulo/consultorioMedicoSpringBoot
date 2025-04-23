package edu.unimagdalena.consultoriomedico.DTO.request;

import edu.unimagdalena.consultoriomedico.enumaration.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentDtoUpdateRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        AppointmentStatus status
) {
}
