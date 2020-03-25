package com.projet.hpd.service;

import com.projet.hpd.domain.Compte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Compte}.
 */
public interface CompteService {

    /**
     * Save a compte.
     *
     * @param compte the entity to save.
     * @return the persisted entity.
     */
    Compte save(Compte compte);

    /**
     * Get all the comptes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Compte> findAll(Pageable pageable);

    /**
     * Get the "id" compte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Compte> findOne(Long id);

    /**
     * Delete the "id" compte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
