package com.projet.hpd.repository;

import com.projet.hpd.domain.CatMagasin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CatMagasin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatMagasinRepository extends JpaRepository<CatMagasin, Long> {

}
