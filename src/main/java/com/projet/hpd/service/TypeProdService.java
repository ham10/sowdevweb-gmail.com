package com.projet.hpd.service;

import com.projet.hpd.domain.TypeProd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeProd}.
 */
public interface TypeProdService {

    /**
     * Save a typeProd.
     *
     * @param typeProd the entity to save.
     * @return the persisted entity.
     */
    TypeProd save(TypeProd typeProd);

    /**
     * Get all the typeProds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeProd> findAll(Pageable pageable);

    /**
     * Get the "id" typeProd.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeProd> findOne(Long id);

    /**
     * Delete the "id" typeProd.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
