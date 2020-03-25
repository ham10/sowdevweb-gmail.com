package com.projet.hpd.repository;

import com.projet.hpd.domain.JourFerie;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JourFerie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourFerieRepository extends JpaRepository<JourFerie, Long> {

}
