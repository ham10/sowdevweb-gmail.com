package com.projet.hpd.service;

import com.projet.hpd.domain.TauxDevise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TauxDevise}.
 */
public interface TauxDeviseService {

    /**
     * Save a tauxDevise.
     *
     * @param tauxDevise the entity to save.
     * @return the persisted entity.
     */
    TauxDevise save(TauxDevise tauxDevise);

    /**
     * Get all the tauxDevises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TauxDevise> findAll(Pageable pageable);

    /**
     * Get the "id" tauxDevise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TauxDevise> findOne(Long id);

    /**
     * Delete the "id" tauxDevise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
