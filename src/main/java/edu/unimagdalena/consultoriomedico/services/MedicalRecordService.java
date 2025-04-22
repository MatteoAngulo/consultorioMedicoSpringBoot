package edu.unimagdalena.consultoriomedico.services;

import edu.unimagdalena.consultoriomedico.DTO.request.MedicalRecordDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.MedicalRecordDtoResponse;

import java.util.List;

public interface MedicalRecordService {

    List<MedicalRecordDtoResponse> findAllMedicalRecords();
    MedicalRecordDtoResponse findById(Long id);
    List<MedicalRecordDtoResponse> findMedicalRecordsByPatient(Long id);
    MedicalRecordDtoResponse saveMedicalRecord(MedicalRecordDtoRequest medicalRecordDtoRequest);
    void deleteMedicalRecord(Long id);
}
