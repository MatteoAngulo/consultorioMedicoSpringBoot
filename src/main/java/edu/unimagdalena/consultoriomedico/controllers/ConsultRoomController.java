package edu.unimagdalena.consultoriomedico.controllers;

import edu.unimagdalena.consultoriomedico.DTO.request.ConsultRoomDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.ConsultRoomDtoResponse;
import edu.unimagdalena.consultoriomedico.services.ConsultRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class ConsultRoomController {

    private final ConsultRoomService consultRoomService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ConsultRoomDtoResponse>> getAllConsultRooms() {
        return ResponseEntity.ok(consultRoomService.findAllConsultRooms());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN')")
    public ResponseEntity<ConsultRoomDtoResponse> getConsultRoomById(@PathVariable Long id){
        return ResponseEntity.ok(consultRoomService.findConsultRoomById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConsultRoomDtoResponse> createConsultRoom(@Valid @RequestBody ConsultRoomDtoRequest consultRoomDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(consultRoomService.saveConsultRoom(consultRoomDtoRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ConsultRoomDtoResponse> updateConsultRoom(@PathVariable Long id, @Valid @RequestBody ConsultRoomDtoRequest consultRoomDtoRequest){
        return ResponseEntity.ok(consultRoomService.updateConsultRoom(id, consultRoomDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteConsultRoom(@PathVariable Long id){
        consultRoomService.deleteConsultRoom(id);
        return ResponseEntity.noContent().build();
    }

}
