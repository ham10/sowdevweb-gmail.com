package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeChamps;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeChamps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeChampsRepository extends JpaRepository<TypeChamps, Long> {

}
