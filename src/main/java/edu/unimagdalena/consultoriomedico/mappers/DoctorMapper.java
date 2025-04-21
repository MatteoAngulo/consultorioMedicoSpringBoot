package edu.unimagdalena.consultoriomedico.mappers;

import ch.qos.logback.core.model.ComponentModel;
import edu.unimagdalena.consultoriomedico.DTO.request.DoctorDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.DoctorDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDtoResponse toDto(Doctor doctor);
    Doctor toEntity(DoctorDtoRequest doctorDto);

}
