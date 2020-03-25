package com.projet.hpd.repository;

import com.projet.hpd.domain.TypePatient;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePatientRepository extends JpaRepository<TypePatient, Long> {

}
