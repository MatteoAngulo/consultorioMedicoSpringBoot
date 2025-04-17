package edu.unimagdalena.consultoriomedico.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPatient;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecords;

    @Column(nullable = false)
    @NotBlank(message = "El nombre no debe estar vacío.")
    private String name;

    @Column(nullable = false)
    @Email(message = "Debe ser un formato email")
    private String email;

    @Column(nullable = false)
    private String phone;

}
