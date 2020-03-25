package com.projet.hpd.service;

import com.projet.hpd.domain.TypeFacture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeFacture}.
 */
public interface TypeFactureService {

    /**
     * Save a typeFacture.
     *
     * @param typeFacture the entity to save.
     * @return the persisted entity.
     */
    TypeFacture save(TypeFacture typeFacture);

    /**
     * Get all the typeFactures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeFacture> findAll(Pageable pageable);

    /**
     * Get the "id" typeFacture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeFacture> findOne(Long id);

    /**
     * Delete the "id" typeFacture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
