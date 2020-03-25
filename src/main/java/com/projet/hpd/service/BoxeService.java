package com.projet.hpd.service;

import com.projet.hpd.domain.Boxe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Boxe}.
 */
public interface BoxeService {

    /**
     * Save a boxe.
     *
     * @param boxe the entity to save.
     * @return the persisted entity.
     */
    Boxe save(Boxe boxe);

    /**
     * Get all the boxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Boxe> findAll(Pageable pageable);

    /**
     * Get the "id" boxe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Boxe> findOne(Long id);

    /**
     * Delete the "id" boxe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
