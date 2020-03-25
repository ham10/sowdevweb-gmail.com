package com.projet.hpd.service;

import com.projet.hpd.domain.Plat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Plat}.
 */
public interface PlatService {

    /**
     * Save a plat.
     *
     * @param plat the entity to save.
     * @return the persisted entity.
     */
    Plat save(Plat plat);

    /**
     * Get all the plats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Plat> findAll(Pageable pageable);

    /**
     * Get the "id" plat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plat> findOne(Long id);

    /**
     * Delete the "id" plat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
