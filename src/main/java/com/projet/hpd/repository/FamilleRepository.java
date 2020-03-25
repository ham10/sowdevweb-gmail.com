package com.projet.hpd.repository;

import com.projet.hpd.domain.Famille;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Famille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilleRepository extends JpaRepository<Famille, Long> {

}
