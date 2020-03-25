package com.projet.hpd.service;

import com.projet.hpd.domain.Specialite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Specialite}.
 */
public interface SpecialiteService {

    /**
     * Save a specialite.
     *
     * @param specialite the entity to save.
     * @return the persisted entity.
     */
    Specialite save(Specialite specialite);

    /**
     * Get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Specialite> findAll(Pageable pageable);

    /**
     * Get the "id" specialite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Specialite> findOne(Long id);

    /**
     * Delete the "id" specialite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
