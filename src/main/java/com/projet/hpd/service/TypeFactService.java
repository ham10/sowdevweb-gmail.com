package com.projet.hpd.service;

import com.projet.hpd.domain.TypeFact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeFact}.
 */
public interface TypeFactService {

    /**
     * Save a typeFact.
     *
     * @param typeFact the entity to save.
     * @return the persisted entity.
     */
    TypeFact save(TypeFact typeFact);

    /**
     * Get all the typeFacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeFact> findAll(Pageable pageable);

    /**
     * Get the "id" typeFact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeFact> findOne(Long id);

    /**
     * Delete the "id" typeFact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
