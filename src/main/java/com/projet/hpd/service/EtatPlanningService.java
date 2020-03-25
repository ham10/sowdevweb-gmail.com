package com.projet.hpd.service;

import com.projet.hpd.domain.EtatPlanning;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EtatPlanning}.
 */
public interface EtatPlanningService {

    /**
     * Save a etatPlanning.
     *
     * @param etatPlanning the entity to save.
     * @return the persisted entity.
     */
    EtatPlanning save(EtatPlanning etatPlanning);

    /**
     * Get all the etatPlannings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatPlanning> findAll(Pageable pageable);

    /**
     * Get the "id" etatPlanning.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatPlanning> findOne(Long id);

    /**
     * Delete the "id" etatPlanning.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
