package com.projet.hpd.repository;

import com.projet.hpd.domain.EtatPlanning;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatPlanning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatPlanningRepository extends JpaRepository<EtatPlanning, Long> {

}
