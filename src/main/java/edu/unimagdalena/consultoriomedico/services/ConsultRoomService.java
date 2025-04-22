package edu.unimagdalena.consultoriomedico.services;

import edu.unimagdalena.consultoriomedico.DTO.request.ConsultRoomDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.ConsultRoomDtoResponse;

import java.util.List;

public interface ConsultRoomService {

    List<ConsultRoomDtoResponse> findAllConsultRooms();
    ConsultRoomDtoResponse findConsultRoomById(Long id);
    ConsultRoomDtoResponse saveConsultRoom(ConsultRoomDtoRequest consultRoomDtoRequest);
    ConsultRoomDtoResponse updateConsultRoom(Long id, ConsultRoomDtoRequest consultRoomDtoRequest);
    void deleteConsultRoom(Long id);
}
