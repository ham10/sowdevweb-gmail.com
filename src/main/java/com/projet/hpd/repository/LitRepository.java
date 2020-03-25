package com.projet.hpd.repository;

import com.projet.hpd.domain.Lit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LitRepository extends JpaRepository<Lit, Long> {

}
