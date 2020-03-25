package com.projet.hpd.repository;

import com.projet.hpd.domain.EtatFacture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatFacture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatFactureRepository extends JpaRepository<EtatFacture, Long> {

}
