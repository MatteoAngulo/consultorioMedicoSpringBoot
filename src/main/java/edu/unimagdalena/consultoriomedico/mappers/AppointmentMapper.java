package edu.unimagdalena.consultoriomedico.mappers;

import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.AppointmentDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source = "patient.idPatient", target = "idPatient")
    @Mapping(source = "doctor.idDoctor", target = "idDoctor")
    @Mapping(source = "consultRoom.idConsultRoom", target = "idConsultRoom")
    AppointmentDtoResponse toAppointmentDtoResponse(Appointment appointment);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "consultRoom", ignore = true)
    Appointment toEntity(AppointmentDtoRequest appointment);
}
