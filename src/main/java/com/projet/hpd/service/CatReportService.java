package com.projet.hpd.service;

import com.projet.hpd.domain.CatReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CatReport}.
 */
public interface CatReportService {

    /**
     * Save a catReport.
     *
     * @param catReport the entity to save.
     * @return the persisted entity.
     */
    CatReport save(CatReport catReport);

    /**
     * Get all the catReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CatReport> findAll(Pageable pageable);

    /**
     * Get the "id" catReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CatReport> findOne(Long id);

    /**
     * Delete the "id" catReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
