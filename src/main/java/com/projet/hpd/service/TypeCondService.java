package com.projet.hpd.service;

import com.projet.hpd.domain.TypeCond;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeCond}.
 */
public interface TypeCondService {

    /**
     * Save a typeCond.
     *
     * @param typeCond the entity to save.
     * @return the persisted entity.
     */
    TypeCond save(TypeCond typeCond);

    /**
     * Get all the typeConds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeCond> findAll(Pageable pageable);

    /**
     * Get the "id" typeCond.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeCond> findOne(Long id);

    /**
     * Delete the "id" typeCond.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
