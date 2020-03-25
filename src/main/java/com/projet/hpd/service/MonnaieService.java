package com.projet.hpd.service;

import com.projet.hpd.domain.Monnaie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Monnaie}.
 */
public interface MonnaieService {

    /**
     * Save a monnaie.
     *
     * @param monnaie the entity to save.
     * @return the persisted entity.
     */
    Monnaie save(Monnaie monnaie);

    /**
     * Get all the monnaies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Monnaie> findAll(Pageable pageable);

    /**
     * Get the "id" monnaie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Monnaie> findOne(Long id);

    /**
     * Delete the "id" monnaie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
