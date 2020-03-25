package com.projet.hpd.repository;

import com.projet.hpd.domain.Calendrier;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Calendrier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendrierRepository extends JpaRepository<Calendrier, Long> {

}
