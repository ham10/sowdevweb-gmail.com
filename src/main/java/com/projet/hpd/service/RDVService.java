package com.projet.hpd.service;

import com.projet.hpd.domain.RDV;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link RDV}.
 */
public interface RDVService {

    /**
     * Save a rDV.
     *
     * @param rDV the entity to save.
     * @return the persisted entity.
     */
    RDV save(RDV rDV);

    /**
     * Get all the rDVS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RDV> findAll(Pageable pageable);

    /**
     * Get the "id" rDV.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RDV> findOne(Long id);

    /**
     * Delete the "id" rDV.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
