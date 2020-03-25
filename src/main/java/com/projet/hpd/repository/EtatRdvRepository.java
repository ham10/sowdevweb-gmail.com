package com.projet.hpd.repository;

import com.projet.hpd.domain.EtatRdv;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatRdv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatRdvRepository extends JpaRepository<EtatRdv, Long> {

}
