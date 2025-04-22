package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class AppointmentNotFoundException extends SourceNotFoundException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
