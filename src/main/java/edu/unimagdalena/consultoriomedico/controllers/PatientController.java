package edu.unimagdalena.consultoriomedico.controllers;

import edu.unimagdalena.consultoriomedico.DTO.request.PatientDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.PatientDtoResponse;
import edu.unimagdalena.consultoriomedico.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<List<PatientDtoResponse>> getAllPatients(){
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN')")
    public ResponseEntity<PatientDtoResponse> getPatientById(@PathVariable Long id){
        return ResponseEntity.ok(patientService.findPatientById(id));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientDtoResponse> createPatient(@RequestBody @Valid PatientDtoRequest patientDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.savePatient(patientDtoRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientDtoResponse> updatePatient(@PathVariable  Long id, @RequestBody @Valid PatientDtoRequest patientDtoRequest){
        return ResponseEntity.ok(patientService.updatePatient(id,patientDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
