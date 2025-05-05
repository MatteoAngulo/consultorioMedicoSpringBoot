package edu.unimagdalena.consultoriomedico.repositories;

import edu.unimagdalena.consultoriomedico.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInterface extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
