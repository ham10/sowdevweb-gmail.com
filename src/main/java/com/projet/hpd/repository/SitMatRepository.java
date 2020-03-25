package com.projet.hpd.repository;

import com.projet.hpd.domain.SitMat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SitMat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SitMatRepository extends JpaRepository<SitMat, Long> {

}
