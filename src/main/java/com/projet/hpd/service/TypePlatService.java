package com.projet.hpd.service;

import com.projet.hpd.domain.TypePlat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypePlat}.
 */
public interface TypePlatService {

    /**
     * Save a typePlat.
     *
     * @param typePlat the entity to save.
     * @return the persisted entity.
     */
    TypePlat save(TypePlat typePlat);

    /**
     * Get all the typePlats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypePlat> findAll(Pageable pageable);

    /**
     * Get the "id" typePlat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypePlat> findOne(Long id);

    /**
     * Delete the "id" typePlat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
