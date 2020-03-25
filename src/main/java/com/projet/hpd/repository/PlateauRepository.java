package com.projet.hpd.repository;

import com.projet.hpd.domain.Plateau;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Plateau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlateauRepository extends JpaRepository<Plateau, Long> {

}
