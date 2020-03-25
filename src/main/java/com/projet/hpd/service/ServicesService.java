package com.projet.hpd.service;

import com.projet.hpd.domain.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Services}.
 */
public interface ServicesService {

    /**
     * Save a services.
     *
     * @param services the entity to save.
     * @return the persisted entity.
     */
    Services save(Services services);

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Services> findAll(Pageable pageable);

    /**
     * Get the "id" services.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Services> findOne(Long id);

    /**
     * Delete the "id" services.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
