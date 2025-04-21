package edu.unimagdalena.consultoriomedico.DTO.response;

import java.time.LocalDateTime;

public record MedicalRecordDtoResponse(
        Long idMedicalRecord,
        PatientDtoResponse patient,
        AppointmentDtoResponse appointment,
        String diagnosis,
        String notes,
        LocalDateTime createdAt
) {}
