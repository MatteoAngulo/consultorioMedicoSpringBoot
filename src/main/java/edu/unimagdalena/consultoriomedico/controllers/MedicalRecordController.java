package edu.unimagdalena.consultoriomedico.controllers;

import edu.unimagdalena.consultoriomedico.DTO.request.MedicalRecordDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.MedicalRecordDtoResponse;
import edu.unimagdalena.consultoriomedico.services.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor

public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MedicalRecordDtoResponse>> getAllMedicalRecords(){
        return ResponseEntity.ok(medicalRecordService.findAllMedicalRecords());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<MedicalRecordDtoResponse> getMedicalRecordById(@PathVariable Long id){
        return ResponseEntity.ok(medicalRecordService.findById(id));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN')")
    public ResponseEntity<List<MedicalRecordDtoResponse>> getMedicalRecordsByPatientId(@PathVariable Long patientId){
        return ResponseEntity.ok(medicalRecordService.findMedicalRecordsByPatient(patientId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<MedicalRecordDtoResponse> createMedicalRecord(@Valid @RequestBody MedicalRecordDtoRequest medicalRecordDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.saveMedicalRecord(medicalRecordDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id){
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }

    
}
