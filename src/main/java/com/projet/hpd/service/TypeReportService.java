package com.projet.hpd.service;

import com.projet.hpd.domain.TypeReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeReport}.
 */
public interface TypeReportService {

    /**
     * Save a typeReport.
     *
     * @param typeReport the entity to save.
     * @return the persisted entity.
     */
    TypeReport save(TypeReport typeReport);

    /**
     * Get all the typeReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeReport> findAll(Pageable pageable);

    /**
     * Get the "id" typeReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeReport> findOne(Long id);

    /**
     * Delete the "id" typeReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
