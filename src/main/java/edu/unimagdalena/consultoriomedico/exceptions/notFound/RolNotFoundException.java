package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class RolNotFoundException extends ResourceNotFoundException {
    public RolNotFoundException(String message) {
        super(message);
    }
}
