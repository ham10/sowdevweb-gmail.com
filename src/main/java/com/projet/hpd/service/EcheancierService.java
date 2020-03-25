package com.projet.hpd.service;

import com.projet.hpd.domain.Echeancier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Echeancier}.
 */
public interface EcheancierService {

    /**
     * Save a echeancier.
     *
     * @param echeancier the entity to save.
     * @return the persisted entity.
     */
    Echeancier save(Echeancier echeancier);

    /**
     * Get all the echeanciers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Echeancier> findAll(Pageable pageable);

    /**
     * Get the "id" echeancier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Echeancier> findOne(Long id);

    /**
     * Delete the "id" echeancier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
