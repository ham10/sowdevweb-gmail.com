package com.projet.hpd.repository;

import com.projet.hpd.domain.Mouvement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mouvement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {

}
