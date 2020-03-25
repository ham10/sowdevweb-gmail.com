package com.projet.hpd.repository;

import com.projet.hpd.domain.AyantDroit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AyantDroit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AyantDroitRepository extends JpaRepository<AyantDroit, Long> {

}
