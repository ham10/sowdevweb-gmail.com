package com.projet.hpd.service;

import com.projet.hpd.domain.SousFamille;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SousFamille}.
 */
public interface SousFamilleService {

    /**
     * Save a sousFamille.
     *
     * @param sousFamille the entity to save.
     * @return the persisted entity.
     */
    SousFamille save(SousFamille sousFamille);

    /**
     * Get all the sousFamilles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SousFamille> findAll(Pageable pageable);

    /**
     * Get the "id" sousFamille.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SousFamille> findOne(Long id);

    /**
     * Delete the "id" sousFamille.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
