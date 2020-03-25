package com.projet.hpd.service;

import com.projet.hpd.domain.EtatBonCom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EtatBonCom}.
 */
public interface EtatBonComService {

    /**
     * Save a etatBonCom.
     *
     * @param etatBonCom the entity to save.
     * @return the persisted entity.
     */
    EtatBonCom save(EtatBonCom etatBonCom);

    /**
     * Get all the etatBonComs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatBonCom> findAll(Pageable pageable);

    /**
     * Get the "id" etatBonCom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatBonCom> findOne(Long id);

    /**
     * Delete the "id" etatBonCom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
