package com.projet.hpd.service;

import com.projet.hpd.domain.Jour;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Jour}.
 */
public interface JourService {

    /**
     * Save a jour.
     *
     * @param jour the entity to save.
     * @return the persisted entity.
     */
    Jour save(Jour jour);

    /**
     * Get all the jours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Jour> findAll(Pageable pageable);

    /**
     * Get the "id" jour.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Jour> findOne(Long id);

    /**
     * Delete the "id" jour.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
