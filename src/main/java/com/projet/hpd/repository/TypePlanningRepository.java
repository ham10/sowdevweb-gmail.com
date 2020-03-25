package com.projet.hpd.repository;

import com.projet.hpd.domain.TypePlanning;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePlanning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePlanningRepository extends JpaRepository<TypePlanning, Long> {

}
