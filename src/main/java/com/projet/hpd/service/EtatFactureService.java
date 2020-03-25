package com.projet.hpd.service;

import com.projet.hpd.domain.EtatFacture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EtatFacture}.
 */
public interface EtatFactureService {

    /**
     * Save a etatFacture.
     *
     * @param etatFacture the entity to save.
     * @return the persisted entity.
     */
    EtatFacture save(EtatFacture etatFacture);

    /**
     * Get all the etatFactures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatFacture> findAll(Pageable pageable);

    /**
     * Get the "id" etatFacture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatFacture> findOne(Long id);

    /**
     * Delete the "id" etatFacture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
