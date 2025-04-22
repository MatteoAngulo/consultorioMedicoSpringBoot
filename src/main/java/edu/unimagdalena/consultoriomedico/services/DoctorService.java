package edu.unimagdalena.consultoriomedico.services;

import edu.unimagdalena.consultoriomedico.DTO.request.DoctorDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.DoctorDtoResponse;

import java.util.List;

public interface DoctorService {

    List<DoctorDtoResponse> findAllDoctors();
    DoctorDtoResponse findDoctorById(Long idDoctor);
    DoctorDtoResponse saveDoctor(DoctorDtoRequest doctorDtoRequest);
    DoctorDtoResponse updateDoctor(Long idDoctor, DoctorDtoRequest doctorDtoRequest);
    void deleteDoctor(Long idDoctor);

}
