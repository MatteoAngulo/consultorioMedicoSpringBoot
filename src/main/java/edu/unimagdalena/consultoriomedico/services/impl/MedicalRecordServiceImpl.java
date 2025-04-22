package edu.unimagdalena.consultoriomedico.services.impl;

import edu.unimagdalena.consultoriomedico.DTO.request.MedicalRecordDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.MedicalRecordDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Appointment;
import edu.unimagdalena.consultoriomedico.entities.MedicalRecord;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import edu.unimagdalena.consultoriomedico.exceptions.AppointmentStillScheduledException;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.AppointmentNotFoundException;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.MedicalRecordNotFoundException;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.PatientNotFoundException;
import edu.unimagdalena.consultoriomedico.mappers.MedicalRecordMapper;
import edu.unimagdalena.consultoriomedico.repositories.AppointmentRepository;
import edu.unimagdalena.consultoriomedico.repositories.ConsultRoomRepository;
import edu.unimagdalena.consultoriomedico.repositories.MedicalRecordRepository;
import edu.unimagdalena.consultoriomedico.repositories.PatientRepository;
import edu.unimagdalena.consultoriomedico.services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final ConsultRoomRepository consultRoomRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;
    private MedicalRecordMapper medicalRecordMapper;


    @Override
    public List<MedicalRecordDtoResponse> findAllMedicalRecords() {
        return medicalRecordRepository.findAll().stream()
                .map(medicalRecordMapper::toMedicalRecordDtoResponse)
                .toList();
    }

    @Override
    public MedicalRecordDtoResponse findById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical Record with ID: " + id + " Not Found"));

        return medicalRecordMapper.toMedicalRecordDtoResponse(medicalRecord);

    }

    @Override
    public List<MedicalRecordDtoResponse> findMedicalRecordsByPatient(Long id) {

        if(!patientRepository.existsById(id)){
            throw new PatientNotFoundException("Patient with ID: " + id + " Not Found");
        }

        List<MedicalRecordDtoResponse> result = medicalRecordRepository.findAllByPatient_IdPatient(id).stream()
                .map(medicalRecordMapper::toMedicalRecordDtoResponse)
                .toList();

        return result;
    }

    @Override
    public MedicalRecordDtoResponse saveMedicalRecord(MedicalRecordDtoRequest medicalRecordDtoRequest) {

        Patient patient = patientRepository.findById(medicalRecordDtoRequest.idPatient())
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID: " + medicalRecordDtoRequest.idPatient() + " Not Found"));

        Appointment appointment = appointmentRepository.findById(medicalRecordDtoRequest.idAppointment())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID: " + medicalRecordDtoRequest.idAppointment() + " Not Found"));

        if(appointment.getStatus().equals("SCHEDULED")){
            throw new AppointmentStillScheduledException("Appointment's status is Scheduled");
        }

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(medicalRecordDtoRequest);
        medicalRecord.setPatient(patient);
        medicalRecord.setAppointment(appointment);

        return medicalRecordMapper.toMedicalRecordDtoResponse(medicalRecord);

    }

    @Override
    public void deleteMedicalRecord(Long id) {

        if(!medicalRecordRepository.existsById(id)){
            throw new MedicalRecordNotFoundException("Medical Record with ID: " + id + " Not Found");
        }

        consultRoomRepository.deleteById(id);

    }
}
