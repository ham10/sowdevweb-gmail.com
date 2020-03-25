package com.projet.hpd.service;

import com.projet.hpd.domain.Immo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Immo}.
 */
public interface ImmoService {

    /**
     * Save a immo.
     *
     * @param immo the entity to save.
     * @return the persisted entity.
     */
    Immo save(Immo immo);

    /**
     * Get all the immos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Immo> findAll(Pageable pageable);

    /**
     * Get the "id" immo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Immo> findOne(Long id);

    /**
     * Delete the "id" immo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
