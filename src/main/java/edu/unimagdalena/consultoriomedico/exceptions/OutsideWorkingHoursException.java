package edu.unimagdalena.consultoriomedico.exceptions;

public class OutsideWorkingHoursException extends RuntimeException {
    public OutsideWorkingHoursException(String message) {
        super(message);
    }
}
