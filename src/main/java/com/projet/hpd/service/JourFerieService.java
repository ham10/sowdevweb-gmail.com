package com.projet.hpd.service;

import com.projet.hpd.domain.JourFerie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link JourFerie}.
 */
public interface JourFerieService {

    /**
     * Save a jourFerie.
     *
     * @param jourFerie the entity to save.
     * @return the persisted entity.
     */
    JourFerie save(JourFerie jourFerie);

    /**
     * Get all the jourFeries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourFerie> findAll(Pageable pageable);

    /**
     * Get the "id" jourFerie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourFerie> findOne(Long id);

    /**
     * Delete the "id" jourFerie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
