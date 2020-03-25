package com.projet.hpd.service;

import com.projet.hpd.domain.Calendrier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Calendrier}.
 */
public interface CalendrierService {

    /**
     * Save a calendrier.
     *
     * @param calendrier the entity to save.
     * @return the persisted entity.
     */
    Calendrier save(Calendrier calendrier);

    /**
     * Get all the calendriers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Calendrier> findAll(Pageable pageable);

    /**
     * Get the "id" calendrier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Calendrier> findOne(Long id);

    /**
     * Delete the "id" calendrier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
