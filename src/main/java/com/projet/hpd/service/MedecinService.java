package com.projet.hpd.service;

import com.projet.hpd.domain.Medecin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Medecin}.
 */
public interface MedecinService {

    /**
     * Save a medecin.
     *
     * @param medecin the entity to save.
     * @return the persisted entity.
     */
    Medecin save(Medecin medecin);

    /**
     * Get all the medecins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Medecin> findAll(Pageable pageable);

    /**
     * Get the "id" medecin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Medecin> findOne(Long id);

    /**
     * Delete the "id" medecin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
