package com.projet.hpd.repository;

import com.projet.hpd.domain.Rayon;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Rayon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RayonRepository extends JpaRepository<Rayon, Long> {

}
