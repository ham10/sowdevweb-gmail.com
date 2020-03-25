package com.projet.hpd.service;

import com.projet.hpd.domain.TypePole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypePole}.
 */
public interface TypePoleService {

    /**
     * Save a typePole.
     *
     * @param typePole the entity to save.
     * @return the persisted entity.
     */
    TypePole save(TypePole typePole);

    /**
     * Get all the typePoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypePole> findAll(Pageable pageable);

    /**
     * Get the "id" typePole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypePole> findOne(Long id);

    /**
     * Delete the "id" typePole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
