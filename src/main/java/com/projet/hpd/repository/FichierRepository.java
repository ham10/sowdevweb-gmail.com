package com.projet.hpd.repository;

import com.projet.hpd.domain.Fichier;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Fichier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichierRepository extends JpaRepository<Fichier, Long> {

}
