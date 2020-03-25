package com.projet.hpd.service;

import com.projet.hpd.domain.ModeRegle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ModeRegle}.
 */
public interface ModeRegleService {

    /**
     * Save a modeRegle.
     *
     * @param modeRegle the entity to save.
     * @return the persisted entity.
     */
    ModeRegle save(ModeRegle modeRegle);

    /**
     * Get all the modeRegles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModeRegle> findAll(Pageable pageable);

    /**
     * Get the "id" modeRegle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModeRegle> findOne(Long id);

    /**
     * Delete the "id" modeRegle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
