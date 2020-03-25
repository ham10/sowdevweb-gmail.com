package com.projet.hpd.repository;

import com.projet.hpd.domain.SousFamille;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SousFamille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousFamilleRepository extends JpaRepository<SousFamille, Long> {

}
