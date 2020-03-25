package com.projet.hpd.service;

import com.projet.hpd.domain.Module;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Module}.
 */
public interface ModuleService {

    /**
     * Save a module.
     *
     * @param module the entity to save.
     * @return the persisted entity.
     */
    Module save(Module module);

    /**
     * Get all the modules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Module> findAll(Pageable pageable);

    /**
     * Get the "id" module.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Module> findOne(Long id);

    /**
     * Delete the "id" module.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
