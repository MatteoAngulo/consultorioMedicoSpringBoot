package edu.unimagdalena.consultoriomedico.exceptions.notFound;

public class MedicalRecordNotFoundException extends ResourceNotFoundException {
    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}
