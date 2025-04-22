package edu.unimagdalena.consultoriomedico.exceptions;

public class AppointmentStillScheduledException extends RuntimeException {
    public AppointmentStillScheduledException(String message) {
        super(message);
    }
}
