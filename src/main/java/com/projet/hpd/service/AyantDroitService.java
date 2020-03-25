package com.projet.hpd.service;

import com.projet.hpd.domain.AyantDroit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AyantDroit}.
 */
public interface AyantDroitService {

    /**
     * Save a ayantDroit.
     *
     * @param ayantDroit the entity to save.
     * @return the persisted entity.
     */
    AyantDroit save(AyantDroit ayantDroit);

    /**
     * Get all the ayantDroits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AyantDroit> findAll(Pageable pageable);

    /**
     * Get the "id" ayantDroit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AyantDroit> findOne(Long id);

    /**
     * Delete the "id" ayantDroit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
