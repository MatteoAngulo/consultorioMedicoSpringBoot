package edu.unimagdalena.consultoriomedico.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumRole {
    ADMIN,
    DOCTOR,
    PATIENT;

    @JsonCreator
    public static EnumRole fromString(String value) {
        return EnumRole.valueOf(value.toUpperCase());
    }
}
