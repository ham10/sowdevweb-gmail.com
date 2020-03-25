package com.projet.hpd.repository;

import com.projet.hpd.domain.Jour;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Jour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourRepository extends JpaRepository<Jour, Long> {

}
