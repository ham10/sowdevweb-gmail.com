package com.projet.hpd.service;

import com.projet.hpd.domain.Devise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Devise}.
 */
public interface DeviseService {

    /**
     * Save a devise.
     *
     * @param devise the entity to save.
     * @return the persisted entity.
     */
    Devise save(Devise devise);

    /**
     * Get all the devises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Devise> findAll(Pageable pageable);

    /**
     * Get the "id" devise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Devise> findOne(Long id);

    /**
     * Delete the "id" devise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
