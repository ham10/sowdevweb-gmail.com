package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeFact;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeFact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeFactRepository extends JpaRepository<TypeFact, Long> {

}
