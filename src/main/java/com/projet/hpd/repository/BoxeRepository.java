package com.projet.hpd.repository;

import com.projet.hpd.domain.Boxe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Boxe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxeRepository extends JpaRepository<Boxe, Long> {

}
