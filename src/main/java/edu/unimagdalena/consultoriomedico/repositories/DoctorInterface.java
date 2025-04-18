package edu.unimagdalena.consultoriomedico.repositories;

import edu.unimagdalena.consultoriomedico.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorInterface extends JpaRepository<Doctor, Long> {


}
