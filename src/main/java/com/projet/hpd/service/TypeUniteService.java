package com.projet.hpd.service;

import com.projet.hpd.domain.TypeUnite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeUnite}.
 */
public interface TypeUniteService {

    /**
     * Save a typeUnite.
     *
     * @param typeUnite the entity to save.
     * @return the persisted entity.
     */
    TypeUnite save(TypeUnite typeUnite);

    /**
     * Get all the typeUnites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeUnite> findAll(Pageable pageable);

    /**
     * Get the "id" typeUnite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeUnite> findOne(Long id);

    /**
     * Delete the "id" typeUnite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
