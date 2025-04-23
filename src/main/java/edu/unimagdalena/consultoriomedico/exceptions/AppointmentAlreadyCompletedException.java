package edu.unimagdalena.consultoriomedico.exceptions;

public class AppointmentAlreadyCompletedException extends RuntimeException {
    public AppointmentAlreadyCompletedException(String message) {
        super(message);
    }
}
