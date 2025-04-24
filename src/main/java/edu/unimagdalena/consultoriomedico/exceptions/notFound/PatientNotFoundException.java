package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class PatientNotFoundException extends ResourceNotFoundException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
