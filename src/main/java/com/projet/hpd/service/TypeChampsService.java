package com.projet.hpd.service;

import com.projet.hpd.domain.TypeChamps;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeChamps}.
 */
public interface TypeChampsService {

    /**
     * Save a typeChamps.
     *
     * @param typeChamps the entity to save.
     * @return the persisted entity.
     */
    TypeChamps save(TypeChamps typeChamps);

    /**
     * Get all the typeChamps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeChamps> findAll(Pageable pageable);

    /**
     * Get the "id" typeChamps.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeChamps> findOne(Long id);

    /**
     * Delete the "id" typeChamps.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
