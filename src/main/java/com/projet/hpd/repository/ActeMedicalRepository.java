package com.projet.hpd.repository;

import com.projet.hpd.domain.ActeMedical;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActeMedical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActeMedicalRepository extends JpaRepository<ActeMedical, Long> {

}
