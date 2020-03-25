package com.projet.hpd.service;

import com.projet.hpd.domain.TypeSortie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeSortie}.
 */
public interface TypeSortieService {

    /**
     * Save a typeSortie.
     *
     * @param typeSortie the entity to save.
     * @return the persisted entity.
     */
    TypeSortie save(TypeSortie typeSortie);

    /**
     * Get all the typeSorties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeSortie> findAll(Pageable pageable);

    /**
     * Get the "id" typeSortie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeSortie> findOne(Long id);

    /**
     * Delete the "id" typeSortie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
