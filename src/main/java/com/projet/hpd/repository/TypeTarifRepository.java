package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeTarif;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeTarif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTarifRepository extends JpaRepository<TypeTarif, Long> {

}
