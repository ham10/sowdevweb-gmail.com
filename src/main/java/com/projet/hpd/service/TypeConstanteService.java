package com.projet.hpd.service;

import com.projet.hpd.domain.TypeConstante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeConstante}.
 */
public interface TypeConstanteService {

    /**
     * Save a typeConstante.
     *
     * @param typeConstante the entity to save.
     * @return the persisted entity.
     */
    TypeConstante save(TypeConstante typeConstante);

    /**
     * Get all the typeConstantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeConstante> findAll(Pageable pageable);

    /**
     * Get the "id" typeConstante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeConstante> findOne(Long id);

    /**
     * Delete the "id" typeConstante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
