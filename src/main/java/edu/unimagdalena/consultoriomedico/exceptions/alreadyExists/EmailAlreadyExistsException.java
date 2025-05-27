package edu.unimagdalena.consultoriomedico.exceptions.alreadyExists;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email: " + email + " already exists");
    }
}
