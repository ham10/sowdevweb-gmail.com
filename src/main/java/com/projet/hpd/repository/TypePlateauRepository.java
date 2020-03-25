package com.projet.hpd.repository;

import com.projet.hpd.domain.TypePlateau;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePlateau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePlateauRepository extends JpaRepository<TypePlateau, Long> {

}
