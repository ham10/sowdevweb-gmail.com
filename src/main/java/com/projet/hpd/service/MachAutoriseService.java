package com.projet.hpd.service;

import com.projet.hpd.domain.MachAutorise;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MachAutorise}.
 */
public interface MachAutoriseService {

    /**
     * Save a machAutorise.
     *
     * @param machAutorise the entity to save.
     * @return the persisted entity.
     */
    MachAutorise save(MachAutorise machAutorise);

    /**
     * Get all the machAutorises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MachAutorise> findAll(Pageable pageable);

    /**
     * Get the "id" machAutorise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MachAutorise> findOne(Long id);

    /**
     * Delete the "id" machAutorise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
