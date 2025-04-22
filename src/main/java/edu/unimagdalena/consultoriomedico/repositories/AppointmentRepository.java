package edu.unimagdalena.consultoriomedico.repositories;

import edu.unimagdalena.consultoriomedico.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.consultRoom.idConsultRoom = :consult_room_id AND (a.startTime < :end_time AND a.endTime > :start_time)")
    List<Appointment> findConflicts(@Param("id_consult_room") Long idConsultRoom,
                                    @Param("start_time") LocalDateTime startTime,
                                    @Param("end_time") LocalDateTime endTime);


}
