package com.projet.hpd.service;

import com.projet.hpd.domain.Famille;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Famille}.
 */
public interface FamilleService {

    /**
     * Save a famille.
     *
     * @param famille the entity to save.
     * @return the persisted entity.
     */
    Famille save(Famille famille);

    /**
     * Get all the familles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Famille> findAll(Pageable pageable);

    /**
     * Get the "id" famille.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Famille> findOne(Long id);

    /**
     * Delete the "id" famille.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
