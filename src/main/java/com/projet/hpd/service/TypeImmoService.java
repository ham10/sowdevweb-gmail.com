package com.projet.hpd.service;

import com.projet.hpd.domain.TypeImmo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeImmo}.
 */
public interface TypeImmoService {

    /**
     * Save a typeImmo.
     *
     * @param typeImmo the entity to save.
     * @return the persisted entity.
     */
    TypeImmo save(TypeImmo typeImmo);

    /**
     * Get all the typeImmos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeImmo> findAll(Pageable pageable);

    /**
     * Get the "id" typeImmo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeImmo> findOne(Long id);

    /**
     * Delete the "id" typeImmo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
