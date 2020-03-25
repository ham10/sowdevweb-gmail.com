package com.projet.hpd.repository;

import com.projet.hpd.domain.TauxDevise;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TauxDevise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TauxDeviseRepository extends JpaRepository<TauxDevise, Long> {

}
