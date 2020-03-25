package com.projet.hpd.repository;

import com.projet.hpd.domain.Echeancier;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Echeancier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcheancierRepository extends JpaRepository<Echeancier, Long> {

}
