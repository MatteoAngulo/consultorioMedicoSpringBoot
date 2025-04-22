package edu.unimagdalena.consultoriomedico.services.impl;

import edu.unimagdalena.consultoriomedico.DTO.request.PatientDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.PatientDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.PatientNotFoundException;
import edu.unimagdalena.consultoriomedico.mappers.PatientMapper;
import edu.unimagdalena.consultoriomedico.repositories.PatientRepository;
import edu.unimagdalena.consultoriomedico.services.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public List<PatientDtoResponse> findAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toPatientDtoResponse)
                .toList();
    }

    @Override
    public PatientDtoResponse findPatientById(Long idPatient) {
        return patientRepository.findById(idPatient)
                .map(patientMapper::toPatientDtoResponse)
                .orElseThrow(() -> new PatientNotFoundException("Patient wiht ID: " + idPatient + " not found"));
    }

    @Override
    public PatientDtoResponse addPatient(PatientDtoRequest patient) {
        Patient patientMapped = patientMapper.toEntity(patient);
        return patientMapper.toPatientDtoResponse(patientRepository.save(patientMapped));
    }

    @Override
    public PatientDtoResponse updatePatient(Long idPatient, PatientDtoRequest patient) {
        Patient patient1 = patientRepository.findById(idPatient)
                .orElseThrow(() -> new PatientNotFoundException("Patient wiht ID: " + idPatient + " not found"));

        patient1.setFullName(patient.fullName());
        patient1.setEmail(patient.email());
        patient1.setPhone(patient.phone());

        return patientMapper.toPatientDtoResponse(patientRepository.save(patient1));
    }

    @Override
    public void deletePatient(Long idPatient) {

        if(!patientRepository.existsById(idPatient)){
            throw new PatientNotFoundException("Patient with ID: " + idPatient + " not found");
        }

        patientRepository.deleteById(idPatient);

    }
}
