package com.projet.hpd.service;

import com.projet.hpd.domain.EtatOperation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link EtatOperation}.
 */
public interface EtatOperationService {

    /**
     * Save a etatOperation.
     *
     * @param etatOperation the entity to save.
     * @return the persisted entity.
     */
    EtatOperation save(EtatOperation etatOperation);

    /**
     * Get all the etatOperations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtatOperation> findAll(Pageable pageable);

    /**
     * Get the "id" etatOperation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtatOperation> findOne(Long id);

    /**
     * Delete the "id" etatOperation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
