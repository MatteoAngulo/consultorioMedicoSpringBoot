package edu.unimagdalena.consultoriomedico.mappers;

import ch.qos.logback.core.model.ComponentModel;
import edu.unimagdalena.consultoriomedico.DTO.request.DoctorDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.DoctorDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDtoResponse toDoctorDtoResponse(Doctor doctor);

    @Mapping(target = "idDoctor", ignore = true)
    Doctor toEntity(DoctorDtoRequest doctorDto);


}
