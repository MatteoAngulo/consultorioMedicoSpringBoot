package edu.unimagdalena.consultoriomedico.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Appointment {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long idAppointment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_patient", referencedColumnName = "idPatient")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_doctor", referencedColumnName = "idDoctor")
    private Doctor doctor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_consultRoom", referencedColumnName = "idConsultRoom")
    private ConsultRoom consultRoom;

    @Column(nullable = false)
    @Future
    private LocalDateTime date;

}
