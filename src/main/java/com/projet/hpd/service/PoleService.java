package com.projet.hpd.service;

import com.projet.hpd.domain.Pole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Pole}.
 */
public interface PoleService {

    /**
     * Save a pole.
     *
     * @param pole the entity to save.
     * @return the persisted entity.
     */
    Pole save(Pole pole);

    /**
     * Get all the poles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pole> findAll(Pageable pageable);

    /**
     * Get the "id" pole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pole> findOne(Long id);

    /**
     * Delete the "id" pole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
