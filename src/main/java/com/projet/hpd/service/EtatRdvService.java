package com.projet.hpd.service;

import com.projet.hpd.domain.EtatRdv;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EtatRdv}.
 */
public interface EtatRdvService {

    /**
     * Save a etatRdv.
     *
     * @param etatRdv the entity to save.
     * @return the persisted entity.
     */
    EtatRdv save(EtatRdv etatRdv);

    /**
     * Get all the etatRdvs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatRdv> findAll(Pageable pageable);

    /**
     * Get the "id" etatRdv.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatRdv> findOne(Long id);

    /**
     * Delete the "id" etatRdv.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
