package edu.unimagdalena.consultoriomedico.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class ConsultRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConsultRoom;

    @OneToMany(mappedBy = "consultRoom")
    private List<Appointment> appointments;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @Positive
    private String floor;

    @Column
    @Size(max = 1000)
    private String description;
}
