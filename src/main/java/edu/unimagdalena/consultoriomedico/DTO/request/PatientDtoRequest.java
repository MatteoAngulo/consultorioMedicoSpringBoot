package edu.unimagdalena.consultoriomedico.DTO.request;


public record PatientDtoRequest(
        String fullName,
        String email,
        String phone) {}
