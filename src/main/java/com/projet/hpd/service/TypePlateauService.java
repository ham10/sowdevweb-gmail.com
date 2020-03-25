package com.projet.hpd.service;

import com.projet.hpd.domain.TypePlateau;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypePlateau}.
 */
public interface TypePlateauService {

    /**
     * Save a typePlateau.
     *
     * @param typePlateau the entity to save.
     * @return the persisted entity.
     */
    TypePlateau save(TypePlateau typePlateau);

    /**
     * Get all the typePlateaus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypePlateau> findAll(Pageable pageable);

    /**
     * Get the "id" typePlateau.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypePlateau> findOne(Long id);

    /**
     * Delete the "id" typePlateau.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
