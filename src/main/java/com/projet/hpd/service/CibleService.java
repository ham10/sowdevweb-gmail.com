package com.projet.hpd.service;

import com.projet.hpd.domain.Cible;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Cible}.
 */
public interface CibleService {

    /**
     * Save a cible.
     *
     * @param cible the entity to save.
     * @return the persisted entity.
     */
    Cible save(Cible cible);

    /**
     * Get all the cibles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cible> findAll(Pageable pageable);

    /**
     * Get the "id" cible.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cible> findOne(Long id);

    /**
     * Delete the "id" cible.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
