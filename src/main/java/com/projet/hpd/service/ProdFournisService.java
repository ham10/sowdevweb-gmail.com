package com.projet.hpd.service;

import com.projet.hpd.domain.ProdFournis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProdFournis}.
 */
public interface ProdFournisService {

    /**
     * Save a prodFournis.
     *
     * @param prodFournis the entity to save.
     * @return the persisted entity.
     */
    ProdFournis save(ProdFournis prodFournis);

    /**
     * Get all the prodFournis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProdFournis> findAll(Pageable pageable);

    /**
     * Get the "id" prodFournis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProdFournis> findOne(Long id);

    /**
     * Delete the "id" prodFournis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
