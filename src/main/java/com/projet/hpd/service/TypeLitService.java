package com.projet.hpd.service;

import com.projet.hpd.domain.TypeLit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeLit}.
 */
public interface TypeLitService {

    /**
     * Save a typeLit.
     *
     * @param typeLit the entity to save.
     * @return the persisted entity.
     */
    TypeLit save(TypeLit typeLit);

    /**
     * Get all the typeLits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeLit> findAll(Pageable pageable);

    /**
     * Get the "id" typeLit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeLit> findOne(Long id);

    /**
     * Delete the "id" typeLit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
