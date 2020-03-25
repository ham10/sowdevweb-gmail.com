package com.projet.hpd.service;

import com.projet.hpd.domain.Evenement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Evenement}.
 */
public interface EvenementService {

    /**
     * Save a evenement.
     *
     * @param evenement the entity to save.
     * @return the persisted entity.
     */
    Evenement save(Evenement evenement);

    /**
     * Get all the evenements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Evenement> findAll(Pageable pageable);

    /**
     * Get the "id" evenement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Evenement> findOne(Long id);

    /**
     * Delete the "id" evenement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
