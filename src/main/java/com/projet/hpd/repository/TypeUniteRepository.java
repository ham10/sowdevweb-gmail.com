package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeUnite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeUnite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeUniteRepository extends JpaRepository<TypeUnite, Long> {

}
