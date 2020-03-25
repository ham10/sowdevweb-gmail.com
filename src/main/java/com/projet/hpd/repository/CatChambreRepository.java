package com.projet.hpd.repository;

import com.projet.hpd.domain.CatChambre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CatChambre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatChambreRepository extends JpaRepository<CatChambre, Long> {

}
