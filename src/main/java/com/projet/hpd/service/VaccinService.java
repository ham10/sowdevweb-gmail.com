package com.projet.hpd.service;

import com.projet.hpd.domain.Vaccin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Vaccin}.
 */
public interface VaccinService {

    /**
     * Save a vaccin.
     *
     * @param vaccin the entity to save.
     * @return the persisted entity.
     */
    Vaccin save(Vaccin vaccin);

    /**
     * Get all the vaccins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Vaccin> findAll(Pageable pageable);

    /**
     * Get the "id" vaccin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vaccin> findOne(Long id);

    /**
     * Delete the "id" vaccin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
