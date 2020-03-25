package com.projet.hpd.service;

import com.projet.hpd.domain.Etablis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Etablis}.
 */
public interface EtablisService {

    /**
     * Save a etablis.
     *
     * @param etablis the entity to save.
     * @return the persisted entity.
     */
    Etablis save(Etablis etablis);

    /**
     * Get all the etablis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Etablis> findAll(Pageable pageable);

    /**
     * Get the "id" etablis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Etablis> findOne(Long id);

    /**
     * Delete the "id" etablis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
