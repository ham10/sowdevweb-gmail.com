package com.projet.hpd.service;

import com.projet.hpd.domain.Reponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Reponse}.
 */
public interface ReponseService {

    /**
     * Save a reponse.
     *
     * @param reponse the entity to save.
     * @return the persisted entity.
     */
    Reponse save(Reponse reponse);

    /**
     * Get all the reponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reponse> findAll(Pageable pageable);

    /**
     * Get the "id" reponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reponse> findOne(Long id);

    /**
     * Delete the "id" reponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
