package com.projet.hpd.repository;

import com.projet.hpd.domain.BonDeCommande;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BonDeCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonDeCommandeRepository extends JpaRepository<BonDeCommande, Long> {

}
