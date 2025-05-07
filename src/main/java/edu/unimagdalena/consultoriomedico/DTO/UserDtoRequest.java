package edu.unimagdalena.consultoriomedico.DTO;

import edu.unimagdalena.consultoriomedico.entities.Role;

import java.util.Set;

public record UserDtoRequest(
        String username,
        String email,
        String password,
        Set<Role> roles) {
}
