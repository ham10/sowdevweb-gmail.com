package com.projet.hpd.service;

import com.projet.hpd.domain.DetailPlanning;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DetailPlanning}.
 */
public interface DetailPlanningService {

    /**
     * Save a detailPlanning.
     *
     * @param detailPlanning the entity to save.
     * @return the persisted entity.
     */
    DetailPlanning save(DetailPlanning detailPlanning);

    /**
     * Get all the detailPlannings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailPlanning> findAll(Pageable pageable);

    /**
     * Get the "id" detailPlanning.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailPlanning> findOne(Long id);

    /**
     * Delete the "id" detailPlanning.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
