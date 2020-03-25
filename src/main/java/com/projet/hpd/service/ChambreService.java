package com.projet.hpd.service;

import com.projet.hpd.domain.Chambre;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Chambre}.
 */
public interface ChambreService {

    /**
     * Save a chambre.
     *
     * @param chambre the entity to save.
     * @return the persisted entity.
     */
    Chambre save(Chambre chambre);

    /**
     * Get all the chambres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Chambre> findAll(Pageable pageable);

    /**
     * Get the "id" chambre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Chambre> findOne(Long id);

    /**
     * Delete the "id" chambre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
