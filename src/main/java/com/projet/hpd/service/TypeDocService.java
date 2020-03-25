package com.projet.hpd.service;

import com.projet.hpd.domain.TypeDoc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeDoc}.
 */
public interface TypeDocService {

    /**
     * Save a typeDoc.
     *
     * @param typeDoc the entity to save.
     * @return the persisted entity.
     */
    TypeDoc save(TypeDoc typeDoc);

    /**
     * Get all the typeDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeDoc> findAll(Pageable pageable);

    /**
     * Get the "id" typeDoc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeDoc> findOne(Long id);

    /**
     * Delete the "id" typeDoc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
