package edu.unimagdalena.consultoriomedico.mappers;

import edu.unimagdalena.consultoriomedico.DTO.request.PatientDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.PatientDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {


    PatientDtoResponse toPatientDtoResponse(Patient patient);
    Patient toEntity(PatientDtoRequest patient);
}
