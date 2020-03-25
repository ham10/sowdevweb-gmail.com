package com.projet.hpd.service;

import com.projet.hpd.domain.Inventaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Inventaire}.
 */
public interface InventaireService {

    /**
     * Save a inventaire.
     *
     * @param inventaire the entity to save.
     * @return the persisted entity.
     */
    Inventaire save(Inventaire inventaire);

    /**
     * Get all the inventaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Inventaire> findAll(Pageable pageable);

    /**
     * Get the "id" inventaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Inventaire> findOne(Long id);

    /**
     * Delete the "id" inventaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
