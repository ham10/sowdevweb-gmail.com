package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeFacture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeFacture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeFactureRepository extends JpaRepository<TypeFacture, Long> {

}
