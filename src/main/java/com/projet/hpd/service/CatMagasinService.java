package com.projet.hpd.service;

import com.projet.hpd.domain.CatMagasin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CatMagasin}.
 */
public interface CatMagasinService {

    /**
     * Save a catMagasin.
     *
     * @param catMagasin the entity to save.
     * @return the persisted entity.
     */
    CatMagasin save(CatMagasin catMagasin);

    /**
     * Get all the catMagasins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CatMagasin> findAll(Pageable pageable);

    /**
     * Get the "id" catMagasin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CatMagasin> findOne(Long id);

    /**
     * Delete the "id" catMagasin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
