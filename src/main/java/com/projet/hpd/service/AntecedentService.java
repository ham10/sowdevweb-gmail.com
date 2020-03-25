package com.projet.hpd.service;

import com.projet.hpd.domain.Antecedent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Antecedent}.
 */
public interface AntecedentService {

    /**
     * Save a antecedent.
     *
     * @param antecedent the entity to save.
     * @return the persisted entity.
     */
    Antecedent save(Antecedent antecedent);

    /**
     * Get all the antecedents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Antecedent> findAll(Pageable pageable);

    /**
     * Get the "id" antecedent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Antecedent> findOne(Long id);

    /**
     * Delete the "id" antecedent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
