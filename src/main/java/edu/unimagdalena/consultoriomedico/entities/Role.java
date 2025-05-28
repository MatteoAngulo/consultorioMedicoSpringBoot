package edu.unimagdalena.consultoriomedico.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "roles")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Enumerated(EnumType.STRING)
    private EnumRole name;

    /*
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();
    */
}
