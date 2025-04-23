package edu.unimagdalena.consultoriomedico.exceptions;

public class DoctorAlreadyBookedException extends RuntimeException {
    public DoctorAlreadyBookedException(String message) {
        super(message);
    }
}
