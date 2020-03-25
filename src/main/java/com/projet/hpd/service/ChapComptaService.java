package com.projet.hpd.service;

import com.projet.hpd.domain.ChapCompta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ChapCompta}.
 */
public interface ChapComptaService {

    /**
     * Save a chapCompta.
     *
     * @param chapCompta the entity to save.
     * @return the persisted entity.
     */
    ChapCompta save(ChapCompta chapCompta);

    /**
     * Get all the chapComptas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChapCompta> findAll(Pageable pageable);

    /**
     * Get the "id" chapCompta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChapCompta> findOne(Long id);

    /**
     * Delete the "id" chapCompta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
