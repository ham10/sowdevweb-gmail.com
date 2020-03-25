package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeReportRepository extends JpaRepository<TypeReport, Long> {

}
