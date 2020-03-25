package com.projet.hpd.service;

import com.projet.hpd.domain.EtatCaisse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EtatCaisse}.
 */
public interface EtatCaisseService {

    /**
     * Save a etatCaisse.
     *
     * @param etatCaisse the entity to save.
     * @return the persisted entity.
     */
    EtatCaisse save(EtatCaisse etatCaisse);

    /**
     * Get all the etatCaisses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatCaisse> findAll(Pageable pageable);

    /**
     * Get the "id" etatCaisse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatCaisse> findOne(Long id);

    /**
     * Delete the "id" etatCaisse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
