package com.projet.hpd.service;

import com.projet.hpd.domain.Unite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Unite}.
 */
public interface UniteService {

    /**
     * Save a unite.
     *
     * @param unite the entity to save.
     * @return the persisted entity.
     */
    Unite save(Unite unite);

    /**
     * Get all the unites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Unite> findAll(Pageable pageable);

    /**
     * Get the "id" unite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Unite> findOne(Long id);

    /**
     * Delete the "id" unite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
