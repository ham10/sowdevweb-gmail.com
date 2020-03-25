package com.projet.hpd.repository;

import com.projet.hpd.domain.Civilite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Civilite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiviliteRepository extends JpaRepository<Civilite, Long> {

}
