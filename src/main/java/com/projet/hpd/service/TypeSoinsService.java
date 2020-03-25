package com.projet.hpd.service;

import com.projet.hpd.domain.TypeSoins;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeSoins}.
 */
public interface TypeSoinsService {

    /**
     * Save a typeSoins.
     *
     * @param typeSoins the entity to save.
     * @return the persisted entity.
     */
    TypeSoins save(TypeSoins typeSoins);

    /**
     * Get all the typeSoins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeSoins> findAll(Pageable pageable);

    /**
     * Get the "id" typeSoins.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeSoins> findOne(Long id);

    /**
     * Delete the "id" typeSoins.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
