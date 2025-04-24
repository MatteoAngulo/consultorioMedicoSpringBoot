package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class AppointmentNotFoundException extends ResourceNotFoundException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
