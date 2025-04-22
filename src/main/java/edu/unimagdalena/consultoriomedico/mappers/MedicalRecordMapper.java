package edu.unimagdalena.consultoriomedico.mappers;

import edu.unimagdalena.consultoriomedico.DTO.request.MedicalRecordDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.MedicalRecordDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "patient.idPatient", target = "idPatient")
    @Mapping(source = "appointment.idAppointment", target = "idAppointment")
    MedicalRecordDtoResponse toMedicalRecordDtoResponse(MedicalRecord medicalRecord);

    //No mappeamos
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    MedicalRecord toEntity(MedicalRecordDtoRequest medicalRecord);
}
