package com.projet.hpd.repository;

import com.projet.hpd.domain.Fonctionnalite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Fonctionnalite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FonctionnaliteRepository extends JpaRepository<Fonctionnalite, Long> {

}
