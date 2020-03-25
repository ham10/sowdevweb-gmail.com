package com.projet.hpd.service;

import com.projet.hpd.domain.CompteGene;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CompteGene}.
 */
public interface CompteGeneService {

    /**
     * Save a compteGene.
     *
     * @param compteGene the entity to save.
     * @return the persisted entity.
     */
    CompteGene save(CompteGene compteGene);

    /**
     * Get all the compteGenes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompteGene> findAll(Pageable pageable);

    /**
     * Get the "id" compteGene.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompteGene> findOne(Long id);

    /**
     * Delete the "id" compteGene.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
