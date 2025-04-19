package edu.unimagdalena.consultoriomedico.repositories;

import edu.unimagdalena.consultoriomedico.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {


}
