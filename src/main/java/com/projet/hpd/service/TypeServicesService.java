package com.projet.hpd.service;

import com.projet.hpd.domain.TypeServices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeServices}.
 */
public interface TypeServicesService {

    /**
     * Save a typeServices.
     *
     * @param typeServices the entity to save.
     * @return the persisted entity.
     */
    TypeServices save(TypeServices typeServices);

    /**
     * Get all the typeServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeServices> findAll(Pageable pageable);

    /**
     * Get the "id" typeServices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeServices> findOne(Long id);

    /**
     * Delete the "id" typeServices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
