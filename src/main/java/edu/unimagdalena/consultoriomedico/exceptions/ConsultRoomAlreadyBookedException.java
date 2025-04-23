package edu.unimagdalena.consultoriomedico.exceptions;

public class ConsultRoomAlreadyBookedException extends RuntimeException {
    public ConsultRoomAlreadyBookedException(String message) {
        super(message);
    }
}
