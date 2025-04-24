package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class DoctorNotFoundException extends ResourceNotFoundException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
