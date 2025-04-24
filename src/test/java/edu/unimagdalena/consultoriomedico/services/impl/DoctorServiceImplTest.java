package edu.unimagdalena.consultoriomedico.services.impl;

import edu.unimagdalena.consultoriomedico.DTO.request.DoctorDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.DoctorDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.Doctor;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.DoctorNotFoundException;
import edu.unimagdalena.consultoriomedico.mappers.DoctorMapper;
import edu.unimagdalena.consultoriomedico.repositories.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorServiceImpl service;

    private Doctor doctor;
    private DoctorDtoRequest dtoRequest;
    private DoctorDtoResponse dtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctor = Doctor.builder()
                .idDoctor(1L)
                .fullName("Dr. Smith")
                .email("smith@clinic.com")
                .speciality("Dermatología")
                .availableFrom(LocalTime.of(9, 0))
                .availableTo(LocalTime.of(17, 0))
                .build();
        dtoRequest = new DoctorDtoRequest(
                "Dr. Jane Doe",
                "jane.doe@clinic.com",
                "Pediatría",
                LocalTime.of(8, 30),
                LocalTime.of(15, 0)
        );
        dtoResponse = new DoctorDtoResponse(
                1L,
                doctor.getFullName(),
                doctor.getEmail(),
                doctor.getSpeciality(),
                doctor.getAvailableFrom(),
                doctor.getAvailableTo()
        );
    }

    @Test
    void findAllDoctors_returnsMappedList() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        when(doctorMapper.toDoctorDtoResponse(doctor)).thenReturn(dtoResponse);

        List<DoctorDtoResponse> result = service.findAllDoctors();

        assertThat(result).containsExactly(dtoResponse);
        verify(doctorRepository).findAll();
        verify(doctorMapper).toDoctorDtoResponse(doctor);
    }

    @Test
    void findDoctorById_whenExists_returnsDto() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDoctorDtoResponse(doctor)).thenReturn(dtoResponse);

        DoctorDtoResponse result = service.findDoctorById(1L);

        assertThat(result).isEqualTo(dtoResponse);
    }

    @Test
    void findDoctorById_whenNotFound_throwsException() {
        when(doctorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(DoctorNotFoundException.class,
                () -> service.findDoctorById(99L));
    }

    @Test
    void saveDoctor_savesAndReturnsDto() {
        Doctor entity = Doctor.builder()
                .fullName(dtoRequest.fullName())
                .email(dtoRequest.email())
                .speciality(dtoRequest.speciality())
                .availableFrom(dtoRequest.availableFrom())
                .availableTo(dtoRequest.availableTo())
                .build();
        when(doctorMapper.toEntity(dtoRequest)).thenReturn(entity);
        when(doctorRepository.save(entity)).thenReturn(doctor);
        when(doctorMapper.toDoctorDtoResponse(doctor)).thenReturn(dtoResponse);

        DoctorDtoResponse result = service.saveDoctor(dtoRequest);

        assertThat(result).isEqualTo(dtoResponse);
        verify(doctorMapper).toEntity(dtoRequest);
        verify(doctorRepository).save(entity);
        verify(doctorMapper).toDoctorDtoResponse(doctor);
    }

    @Test
    void updateDoctor_whenExists_updatesAndReturnsDto() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDoctorDtoResponse(doctor)).thenReturn(dtoResponse);

        DoctorDtoResponse result = service.updateDoctor(1L, dtoRequest);

        assertThat(doctor.getFullName()).isEqualTo(dtoRequest.fullName());
        assertThat(doctor.getEmail()).isEqualTo(dtoRequest.email());
        assertThat(doctor.getSpeciality()).isEqualTo(dtoRequest.speciality());
        assertThat(doctor.getAvailableFrom()).isEqualTo(dtoRequest.availableFrom());
        assertThat(doctor.getAvailableTo()).isEqualTo(dtoRequest.availableTo());
        assertThat(result).isEqualTo(dtoResponse);
        verify(doctorRepository).save(doctor);
    }

    @Test
    void updateDoctor_whenNotFound_throwsException() {
        when(doctorRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(DoctorNotFoundException.class,
                () -> service.updateDoctor(2L, dtoRequest));
    }

    @Test
    void deleteDoctor_whenNotExists_throwsException() {
        when(doctorRepository.existsById(10L)).thenReturn(false);
        assertThrows(DoctorNotFoundException.class,
                () -> service.deleteDoctor(10L));
    }

    @Test
    void deleteDoctor_whenExists_deletes() {
        when(doctorRepository.existsById(1L)).thenReturn(true);

        service.deleteDoctor(1L);

        verify(doctorRepository).deleteById(1L);
    }
}
