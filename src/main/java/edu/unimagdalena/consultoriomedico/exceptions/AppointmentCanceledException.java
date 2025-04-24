package edu.unimagdalena.consultoriomedico.exceptions;

public class AppointmentCanceledException extends RuntimeException {
    public AppointmentCanceledException(String message) {
        super(message);
    }
}
