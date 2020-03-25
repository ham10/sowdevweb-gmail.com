package com.projet.hpd.repository;

import com.projet.hpd.domain.ProdFournis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProdFournis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdFournisRepository extends JpaRepository<ProdFournis, Long> {

}
