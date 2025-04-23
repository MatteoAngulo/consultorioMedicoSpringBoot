package edu.unimagdalena.consultoriomedico.services;

import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.request.AppointmentDtoUpdateRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.AppointmentDtoResponse;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDtoResponse> findAllAppointments();
    AppointmentDtoResponse findAppointmentById(Long id);
    AppointmentDtoResponse saveAppointment(AppointmentDtoRequest appointmentDtoRequest);
    AppointmentDtoResponse updateAppointment(Long id, AppointmentDtoUpdateRequest appointmentDtoRequest);
    void deleteAppointment(Long id);

}
