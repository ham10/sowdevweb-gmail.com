package com.projet.hpd.service;

import com.projet.hpd.domain.Ecriture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Ecriture}.
 */
public interface EcritureService {

    /**
     * Save a ecriture.
     *
     * @param ecriture the entity to save.
     * @return the persisted entity.
     */
    Ecriture save(Ecriture ecriture);

    /**
     * Get all the ecritures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ecriture> findAll(Pageable pageable);

    /**
     * Get the "id" ecriture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ecriture> findOne(Long id);

    /**
     * Delete the "id" ecriture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
