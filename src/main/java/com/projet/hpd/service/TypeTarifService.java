package com.projet.hpd.service;

import com.projet.hpd.domain.TypeTarif;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeTarif}.
 */
public interface TypeTarifService {

    /**
     * Save a typeTarif.
     *
     * @param typeTarif the entity to save.
     * @return the persisted entity.
     */
    TypeTarif save(TypeTarif typeTarif);

    /**
     * Get all the typeTarifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeTarif> findAll(Pageable pageable);

    /**
     * Get the "id" typeTarif.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeTarif> findOne(Long id);

    /**
     * Delete the "id" typeTarif.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
