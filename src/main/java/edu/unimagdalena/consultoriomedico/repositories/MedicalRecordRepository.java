package edu.unimagdalena.consultoriomedico.repositories;

import edu.unimagdalena.consultoriomedico.entities.MedicalRecord;
import edu.unimagdalena.consultoriomedico.entities.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findAllByPatient_IdPatient(Long IdPatient);

    @Transactional
    @Modifying
    @Query("DELETE FROM MedicalRecord m WHERE m.idMedicalRecord = :id")
    void deleteMedicalRecordById(@Param("id") Long idMedicalRecord);
 }
