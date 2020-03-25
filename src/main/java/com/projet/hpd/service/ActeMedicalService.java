package com.projet.hpd.service;

import com.projet.hpd.domain.ActeMedical;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ActeMedical}.
 */
public interface ActeMedicalService {

    /**
     * Save a acteMedical.
     *
     * @param acteMedical the entity to save.
     * @return the persisted entity.
     */
    ActeMedical save(ActeMedical acteMedical);

    /**
     * Get all the acteMedicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActeMedical> findAll(Pageable pageable);

    /**
     * Get the "id" acteMedical.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActeMedical> findOne(Long id);

    /**
     * Delete the "id" acteMedical.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
