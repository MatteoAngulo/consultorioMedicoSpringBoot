package edu.unimagdalena.consultoriomedico.services;

import edu.unimagdalena.consultoriomedico.DTO.request.PatientDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.PatientDtoResponse;

import java.util.List;

public interface PatientService {

    List<PatientDtoResponse> findAllPatients();
    PatientDtoResponse findPatientById(Long idPatient);
    PatientDtoResponse addPatient(PatientDtoRequest patient);
    PatientDtoResponse updatePatient(Long idPatient, PatientDtoRequest patient);
    void deletePatient(Long idPatient);

}
