package com.projet.hpd.service;

import com.projet.hpd.domain.SitMat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SitMat}.
 */
public interface SitMatService {

    /**
     * Save a sitMat.
     *
     * @param sitMat the entity to save.
     * @return the persisted entity.
     */
    SitMat save(SitMat sitMat);

    /**
     * Get all the sitMats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SitMat> findAll(Pageable pageable);

    /**
     * Get the "id" sitMat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SitMat> findOne(Long id);

    /**
     * Delete the "id" sitMat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
