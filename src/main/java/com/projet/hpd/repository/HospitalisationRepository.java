package com.projet.hpd.repository;

import com.projet.hpd.domain.Hospitalisation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Hospitalisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HospitalisationRepository extends JpaRepository<Hospitalisation, Long> {

}
