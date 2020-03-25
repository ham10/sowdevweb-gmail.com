package com.projet.hpd.service;

import com.projet.hpd.domain.SchemaCompta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SchemaCompta}.
 */
public interface SchemaComptaService {

    /**
     * Save a schemaCompta.
     *
     * @param schemaCompta the entity to save.
     * @return the persisted entity.
     */
    SchemaCompta save(SchemaCompta schemaCompta);

    /**
     * Get all the schemaComptas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchemaCompta> findAll(Pageable pageable);

    /**
     * Get the "id" schemaCompta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchemaCompta> findOne(Long id);

    /**
     * Delete the "id" schemaCompta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
