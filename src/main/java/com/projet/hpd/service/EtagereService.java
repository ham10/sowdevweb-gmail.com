package com.projet.hpd.service;

import com.projet.hpd.domain.Etagere;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Etagere}.
 */
public interface EtagereService {

    /**
     * Save a etagere.
     *
     * @param etagere the entity to save.
     * @return the persisted entity.
     */
    Etagere save(Etagere etagere);

    /**
     * Get all the etageres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Etagere> findAll(Pageable pageable);

    /**
     * Get the "id" etagere.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Etagere> findOne(Long id);

    /**
     * Delete the "id" etagere.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
