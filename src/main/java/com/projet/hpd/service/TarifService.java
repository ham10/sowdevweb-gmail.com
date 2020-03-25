package com.projet.hpd.service;

import com.projet.hpd.domain.Tarif;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Tarif}.
 */
public interface TarifService {

    /**
     * Save a tarif.
     *
     * @param tarif the entity to save.
     * @return the persisted entity.
     */
    Tarif save(Tarif tarif);

    /**
     * Get all the tarifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tarif> findAll(Pageable pageable);

    /**
     * Get the "id" tarif.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tarif> findOne(Long id);

    /**
     * Delete the "id" tarif.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
