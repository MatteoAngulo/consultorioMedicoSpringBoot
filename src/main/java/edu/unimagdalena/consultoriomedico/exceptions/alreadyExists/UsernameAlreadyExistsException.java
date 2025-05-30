package edu.unimagdalena.consultoriomedico.exceptions.alreadyExists;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username: " + username + "already exists.");
    }
}
