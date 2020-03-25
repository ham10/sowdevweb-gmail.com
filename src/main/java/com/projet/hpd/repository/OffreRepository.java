package com.projet.hpd.repository;

import com.projet.hpd.domain.Offre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Offre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {

}
