package com.projet.hpd.repository;

import com.projet.hpd.domain.Immo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Immo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImmoRepository extends JpaRepository<Immo, Long> {

}
