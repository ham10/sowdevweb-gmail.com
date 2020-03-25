package com.projet.hpd.repository;

import com.projet.hpd.domain.EtatImmo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatImmo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatImmoRepository extends JpaRepository<EtatImmo, Long> {

}
