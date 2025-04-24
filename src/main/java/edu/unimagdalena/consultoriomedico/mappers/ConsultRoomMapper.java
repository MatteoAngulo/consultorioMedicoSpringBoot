package edu.unimagdalena.consultoriomedico.mappers;

import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoUpdateRequest;
import edu.unimagdalena.consultoriomedico.DTO.request.ConsultRoomDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.ConsultRoomDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Appointment;
import edu.unimagdalena.consultoriomedico.entities.ConsultRoom;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {

    ConsultRoomDtoResponse toConsultRoomDtoResponse(ConsultRoom consultRoom);
    ConsultRoom toEntity(ConsultRoomDtoRequest consultRoomDto);


    void updateConsultRoomFromDto(ConsultRoomDtoRequest dto, @MappingTarget ConsultRoom consultRoom);
}
