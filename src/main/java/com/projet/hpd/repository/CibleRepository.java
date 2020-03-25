package com.projet.hpd.repository;

import com.projet.hpd.domain.Cible;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cible entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CibleRepository extends JpaRepository<Cible, Long> {

}
