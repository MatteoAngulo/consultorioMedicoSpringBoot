package edu.unimagdalena.consultoriomedico.services.impl;

import edu.unimagdalena.consultoriomedico.DTO.request.ConsultRoomDtoRequest;
import edu.unimagdalena.consultoriomedico.DTO.response.ConsultRoomDtoResponse;
import edu.unimagdalena.consultoriomedico.entities.ConsultRoom;
import edu.unimagdalena.consultoriomedico.exceptions.notFound.ConsultRoomNotFoundException;
import edu.unimagdalena.consultoriomedico.mappers.ConsultRoomMapper;
import edu.unimagdalena.consultoriomedico.repositories.ConsultRoomRepository;
import edu.unimagdalena.consultoriomedico.services.ConsultRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultRoomServiceImpl implements ConsultRoomService {

    private ConsultRoomRepository consultRoomRepository;
    private ConsultRoomMapper consultRoomMapper;

    @Override
    public List<ConsultRoomDtoResponse> findAllConsultRooms() {
        return consultRoomRepository.findAll().stream()
                .map(consultRoomMapper::toConsultRoomDtoResponse)
                .toList();
    }

    @Override
    public ConsultRoomDtoResponse findConsultRoomById(Long id) {
        ConsultRoom consultRoom = consultRoomRepository.findById(id)
                .orElseThrow(() -> new ConsultRoomNotFoundException("Consult Room with ID: " + id + " Not Found"));

        return consultRoomMapper.toConsultRoomDtoResponse(consultRoom);
    }

    @Override
    public ConsultRoomDtoResponse saveConsultRoom(ConsultRoomDtoRequest consultRoomDtoRequest) {
        ConsultRoom toBeSaved = consultRoomMapper.toEntity(consultRoomDtoRequest);
        return consultRoomMapper.toConsultRoomDtoResponse(consultRoomRepository.save(toBeSaved));
    }

    @Override
    public ConsultRoomDtoResponse updateConsultRoom(Long id, ConsultRoomDtoRequest consultRoomDtoRequest) {
        ConsultRoom consultRoom = consultRoomRepository.findById(id)
                .orElseThrow(() -> new ConsultRoomNotFoundException("Consult Room with ID: " + id + " Not Found"));

        consultRoom.setName(consultRoomDtoRequest.name());
        consultRoom.setFloor(consultRoomDtoRequest.floor());
        consultRoom.setDescription(consultRoomDtoRequest.description());

        return consultRoomMapper.toConsultRoomDtoResponse(consultRoomRepository.save(consultRoom));

    }

    @Override
    public void deleteConsultRoom(Long id) {

        if(!consultRoomRepository.existsById(id)){
            throw new ConsultRoomNotFoundException("Consult Room with ID: " + id + " Not Found");
        }

        consultRoomRepository.deleteById(id);
    }

}
