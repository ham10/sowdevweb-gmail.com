package com.projet.hpd.service;

import com.projet.hpd.domain.DosMedical;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DosMedical}.
 */
public interface DosMedicalService {

    /**
     * Save a dosMedical.
     *
     * @param dosMedical the entity to save.
     * @return the persisted entity.
     */
    DosMedical save(DosMedical dosMedical);

    /**
     * Get all the dosMedicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DosMedical> findAll(Pageable pageable);

    /**
     * Get all the dosMedicals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DosMedical> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dosMedical.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DosMedical> findOne(Long id);

    /**
     * Delete the "id" dosMedical.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
