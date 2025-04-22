package edu.unimagdalena.consultoriomedico.exceptions;

public class NoMedicalRecordFoundException extends RuntimeException {
    public NoMedicalRecordFoundException(String message) {
        super(message);
    }
}
