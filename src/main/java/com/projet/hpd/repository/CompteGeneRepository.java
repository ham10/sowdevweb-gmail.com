package com.projet.hpd.repository;

import com.projet.hpd.domain.CompteGene;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompteGene entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteGeneRepository extends JpaRepository<CompteGene, Long> {

}
