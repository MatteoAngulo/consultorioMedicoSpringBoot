package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class PatientNotFoundException extends SourceNotFoundException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
