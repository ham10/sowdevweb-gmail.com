package com.projet.hpd.service;

import com.projet.hpd.domain.FormeProd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FormeProd}.
 */
public interface FormeProdService {

    /**
     * Save a formeProd.
     *
     * @param formeProd the entity to save.
     * @return the persisted entity.
     */
    FormeProd save(FormeProd formeProd);

    /**
     * Get all the formeProds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormeProd> findAll(Pageable pageable);

    /**
     * Get the "id" formeProd.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormeProd> findOne(Long id);

    /**
     * Delete the "id" formeProd.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
