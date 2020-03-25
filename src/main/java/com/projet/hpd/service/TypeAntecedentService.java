package com.projet.hpd.service;

import com.projet.hpd.domain.TypeAntecedent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeAntecedent}.
 */
public interface TypeAntecedentService {

    /**
     * Save a typeAntecedent.
     *
     * @param typeAntecedent the entity to save.
     * @return the persisted entity.
     */
    TypeAntecedent save(TypeAntecedent typeAntecedent);

    /**
     * Get all the typeAntecedents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeAntecedent> findAll(Pageable pageable);

    /**
     * Get the "id" typeAntecedent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeAntecedent> findOne(Long id);

    /**
     * Delete the "id" typeAntecedent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
