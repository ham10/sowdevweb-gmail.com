package com.projet.hpd.service;

import com.projet.hpd.domain.FicheMedical;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FicheMedical}.
 */
public interface FicheMedicalService {

    /**
     * Save a ficheMedical.
     *
     * @param ficheMedical the entity to save.
     * @return the persisted entity.
     */
    FicheMedical save(FicheMedical ficheMedical);

    /**
     * Get all the ficheMedicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FicheMedical> findAll(Pageable pageable);

    /**
     * Get all the ficheMedicals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<FicheMedical> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ficheMedical.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FicheMedical> findOne(Long id);

    /**
     * Delete the "id" ficheMedical.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
