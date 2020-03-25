package com.projet.hpd.repository;

import com.projet.hpd.domain.Caisse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Caisse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaisseRepository extends JpaRepository<Caisse, Long> {

}
