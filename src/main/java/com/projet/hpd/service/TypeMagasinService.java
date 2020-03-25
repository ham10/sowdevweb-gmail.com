package com.projet.hpd.service;

import com.projet.hpd.domain.TypeMagasin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeMagasin}.
 */
public interface TypeMagasinService {

    /**
     * Save a typeMagasin.
     *
     * @param typeMagasin the entity to save.
     * @return the persisted entity.
     */
    TypeMagasin save(TypeMagasin typeMagasin);

    /**
     * Get all the typeMagasins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeMagasin> findAll(Pageable pageable);

    /**
     * Get the "id" typeMagasin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeMagasin> findOne(Long id);

    /**
     * Delete the "id" typeMagasin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
