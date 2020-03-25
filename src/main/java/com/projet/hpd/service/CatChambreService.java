package com.projet.hpd.service;

import com.projet.hpd.domain.CatChambre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CatChambre}.
 */
public interface CatChambreService {

    /**
     * Save a catChambre.
     *
     * @param catChambre the entity to save.
     * @return the persisted entity.
     */
    CatChambre save(CatChambre catChambre);

    /**
     * Get all the catChambres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CatChambre> findAll(Pageable pageable);

    /**
     * Get the "id" catChambre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CatChambre> findOne(Long id);

    /**
     * Delete the "id" catChambre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
