package com.projet.hpd.service;

import com.projet.hpd.domain.Planning;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Planning}.
 */
public interface PlanningService {

    /**
     * Save a planning.
     *
     * @param planning the entity to save.
     * @return the persisted entity.
     */
    Planning save(Planning planning);

    /**
     * Get all the plannings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Planning> findAll(Pageable pageable);

    /**
     * Get the "id" planning.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Planning> findOne(Long id);

    /**
     * Delete the "id" planning.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
