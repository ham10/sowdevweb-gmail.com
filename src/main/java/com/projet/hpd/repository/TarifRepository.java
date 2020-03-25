package com.projet.hpd.repository;

import com.projet.hpd.domain.Tarif;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tarif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {

}
