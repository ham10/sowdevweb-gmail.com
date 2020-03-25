package com.projet.hpd.repository;

import com.projet.hpd.domain.EtatCaisse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatCaisse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatCaisseRepository extends JpaRepository<EtatCaisse, Long> {

}
