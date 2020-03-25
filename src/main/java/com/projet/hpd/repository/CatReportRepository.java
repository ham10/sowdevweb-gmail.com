package com.projet.hpd.repository;

import com.projet.hpd.domain.CatReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CatReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatReportRepository extends JpaRepository<CatReport, Long> {

}
