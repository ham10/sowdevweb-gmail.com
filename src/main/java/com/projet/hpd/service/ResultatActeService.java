package com.projet.hpd.service;

import com.projet.hpd.domain.ResultatActe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ResultatActe}.
 */
public interface ResultatActeService {

    /**
     * Save a resultatActe.
     *
     * @param resultatActe the entity to save.
     * @return the persisted entity.
     */
    ResultatActe save(ResultatActe resultatActe);

    /**
     * Get all the resultatActes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResultatActe> findAll(Pageable pageable);

    /**
     * Get all the resultatActes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ResultatActe> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" resultatActe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultatActe> findOne(Long id);

    /**
     * Delete the "id" resultatActe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
