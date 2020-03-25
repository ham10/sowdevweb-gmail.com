package com.projet.hpd.service;

import com.projet.hpd.domain.Mouvement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Mouvement}.
 */
public interface MouvementService {

    /**
     * Save a mouvement.
     *
     * @param mouvement the entity to save.
     * @return the persisted entity.
     */
    Mouvement save(Mouvement mouvement);

    /**
     * Get all the mouvements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Mouvement> findAll(Pageable pageable);

    /**
     * Get the "id" mouvement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mouvement> findOne(Long id);

    /**
     * Delete the "id" mouvement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
