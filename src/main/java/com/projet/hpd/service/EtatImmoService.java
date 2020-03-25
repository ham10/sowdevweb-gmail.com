package com.projet.hpd.service;

import com.projet.hpd.domain.EtatImmo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EtatImmo}.
 */
public interface EtatImmoService {

    /**
     * Save a etatImmo.
     *
     * @param etatImmo the entity to save.
     * @return the persisted entity.
     */
    EtatImmo save(EtatImmo etatImmo);

    /**
     * Get all the etatImmos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatImmo> findAll(Pageable pageable);

    /**
     * Get the "id" etatImmo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatImmo> findOne(Long id);

    /**
     * Delete the "id" etatImmo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
