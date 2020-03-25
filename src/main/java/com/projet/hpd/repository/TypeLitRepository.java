package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeLit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeLit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeLitRepository extends JpaRepository<TypeLit, Long> {

}
