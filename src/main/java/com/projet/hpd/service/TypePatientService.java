package com.projet.hpd.service;

import com.projet.hpd.domain.TypePatient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypePatient}.
 */
public interface TypePatientService {

    /**
     * Save a typePatient.
     *
     * @param typePatient the entity to save.
     * @return the persisted entity.
     */
    TypePatient save(TypePatient typePatient);

    /**
     * Get all the typePatients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypePatient> findAll(Pageable pageable);

    /**
     * Get the "id" typePatient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypePatient> findOne(Long id);

    /**
     * Delete the "id" typePatient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
