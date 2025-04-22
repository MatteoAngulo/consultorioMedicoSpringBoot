package edu.unimagdalena.consultoriomedico.DTO.response;

import java.time.LocalDateTime;

public record MedicalRecordDtoResponse(
        Long idMedicalRecord,
        Long idPatient,
        Long idAppointment,
        String diagnosis,
        String notes,
        LocalDateTime createdAt
) {}
