package com.projet.hpd.service;

import com.projet.hpd.domain.ParamDivers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ParamDivers}.
 */
public interface ParamDiversService {

    /**
     * Save a paramDivers.
     *
     * @param paramDivers the entity to save.
     * @return the persisted entity.
     */
    ParamDivers save(ParamDivers paramDivers);

    /**
     * Get all the paramDivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParamDivers> findAll(Pageable pageable);

    /**
     * Get the "id" paramDivers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParamDivers> findOne(Long id);

    /**
     * Delete the "id" paramDivers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
