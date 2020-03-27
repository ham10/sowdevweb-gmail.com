package com.projet.hpd.repository;

import com.projet.hpd.domain.Patient;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Optional<Patient> findPatientByCodePatient(String code);
}
