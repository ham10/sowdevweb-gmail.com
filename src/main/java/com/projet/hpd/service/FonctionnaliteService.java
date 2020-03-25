package com.projet.hpd.service;

import com.projet.hpd.domain.Fonctionnalite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Fonctionnalite}.
 */
public interface FonctionnaliteService {

    /**
     * Save a fonctionnalite.
     *
     * @param fonctionnalite the entity to save.
     * @return the persisted entity.
     */
    Fonctionnalite save(Fonctionnalite fonctionnalite);

    /**
     * Get all the fonctionnalites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fonctionnalite> findAll(Pageable pageable);

    /**
     * Get the "id" fonctionnalite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fonctionnalite> findOne(Long id);

    /**
     * Delete the "id" fonctionnalite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
