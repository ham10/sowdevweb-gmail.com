package com.projet.hpd.service;

import com.projet.hpd.domain.Civilite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Civilite}.
 */
public interface CiviliteService {

    /**
     * Save a civilite.
     *
     * @param civilite the entity to save.
     * @return the persisted entity.
     */
    Civilite save(Civilite civilite);

    /**
     * Get all the civilites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Civilite> findAll(Pageable pageable);

    /**
     * Get the "id" civilite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Civilite> findOne(Long id);

    /**
     * Delete the "id" civilite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
