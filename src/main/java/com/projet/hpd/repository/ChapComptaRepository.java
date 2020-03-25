package com.projet.hpd.repository;

import com.projet.hpd.domain.ChapCompta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ChapCompta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChapComptaRepository extends JpaRepository<ChapCompta, Long> {

}
