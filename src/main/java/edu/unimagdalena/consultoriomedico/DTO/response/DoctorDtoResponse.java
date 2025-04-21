package edu.unimagdalena.consultoriomedico.DTO.response;

import java.time.LocalTime;

public record DoctorDtoResponse(Long idDoctor, String fullName, String email,
                                String speciality, LocalTime availabreFrom,
                                LocalTime availableTo) {
}
