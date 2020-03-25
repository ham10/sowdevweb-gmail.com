package com.projet.hpd.repository;

import com.projet.hpd.domain.LigneLivraison;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LigneLivraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneLivraisonRepository extends JpaRepository<LigneLivraison, Long> {

}
