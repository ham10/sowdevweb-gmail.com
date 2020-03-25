package com.projet.hpd.repository;

import com.projet.hpd.domain.DetailPlanning;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DetailPlanning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailPlanningRepository extends JpaRepository<DetailPlanning, Long> {

}
