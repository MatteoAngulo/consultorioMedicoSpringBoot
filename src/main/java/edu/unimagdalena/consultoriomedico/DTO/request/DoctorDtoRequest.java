package edu.unimagdalena.consultoriomedico.DTO.request;

import java.time.LocalTime;

public record DoctorDtoRequest(String fullName,
                               String email,
                               String speciality,
                               LocalTime availableFrom,
                               LocalTime availableTo) {
}
