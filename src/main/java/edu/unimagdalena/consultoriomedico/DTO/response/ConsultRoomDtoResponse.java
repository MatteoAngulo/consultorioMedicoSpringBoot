package edu.unimagdalena.consultoriomedico.DTO.response;

public record ConsultRoomDtoResponse(
        Long id,
        String name,
        String floor,
        String description)
    {
}
