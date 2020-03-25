package com.projet.hpd.repository;

import com.projet.hpd.domain.Pole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoleRepository extends JpaRepository<Pole, Long> {

}
