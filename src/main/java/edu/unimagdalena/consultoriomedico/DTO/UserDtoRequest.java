package edu.unimagdalena.consultoriomedico.DTO;

import edu.unimagdalena.consultoriomedico.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserDtoRequest(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String password,
        @NotNull Set<String> roles) {
}
