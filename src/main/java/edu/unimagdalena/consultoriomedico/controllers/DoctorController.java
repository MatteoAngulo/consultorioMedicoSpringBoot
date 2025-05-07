package edu.unimagdalena.consultoriomedico.controllers;

import edu.unimagdalena.consultoriomedico.DTO.request.DoctorDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.DoctorDtoResponse;
import edu.unimagdalena.consultoriomedico.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DoctorDtoResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorDtoResponse> getDoctorById(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.findDoctorById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorDtoResponse> createDoctor(@Valid @RequestBody DoctorDtoRequest doctorDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.saveDoctor(doctorDtoRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorDtoResponse> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDtoRequest doctorDtoRequest){
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }


}
