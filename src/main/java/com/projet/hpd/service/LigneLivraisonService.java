package com.projet.hpd.service;

import com.projet.hpd.domain.LigneLivraison;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link LigneLivraison}.
 */
public interface LigneLivraisonService {

    /**
     * Save a ligneLivraison.
     *
     * @param ligneLivraison the entity to save.
     * @return the persisted entity.
     */
    LigneLivraison save(LigneLivraison ligneLivraison);

    /**
     * Get all the ligneLivraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LigneLivraison> findAll(Pageable pageable);

    /**
     * Get the "id" ligneLivraison.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneLivraison> findOne(Long id);

    /**
     * Delete the "id" ligneLivraison.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
