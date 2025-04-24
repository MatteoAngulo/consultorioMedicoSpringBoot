package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class ConsultRoomNotFoundException extends ResourceNotFoundException {
    public ConsultRoomNotFoundException(String message) {
        super(message);
    }
}
