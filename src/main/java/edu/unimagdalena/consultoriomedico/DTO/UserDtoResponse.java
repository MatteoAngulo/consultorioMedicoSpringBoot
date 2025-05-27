package edu.unimagdalena.consultoriomedico.DTO;

import edu.unimagdalena.consultoriomedico.entities.Role;

import java.util.Set;

public record UserDtoResponse(
        String username,
        String email,
        Set<String> roles
) {
}
