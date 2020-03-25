package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeConstante;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeConstante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeConstanteRepository extends JpaRepository<TypeConstante, Long> {

}
