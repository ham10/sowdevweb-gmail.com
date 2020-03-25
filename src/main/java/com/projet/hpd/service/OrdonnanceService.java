package com.projet.hpd.service;

import com.projet.hpd.domain.Ordonnance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Ordonnance}.
 */
public interface OrdonnanceService {

    /**
     * Save a ordonnance.
     *
     * @param ordonnance the entity to save.
     * @return the persisted entity.
     */
    Ordonnance save(Ordonnance ordonnance);

    /**
     * Get all the ordonnances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ordonnance> findAll(Pageable pageable);

    /**
     * Get the "id" ordonnance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ordonnance> findOne(Long id);

    /**
     * Delete the "id" ordonnance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
