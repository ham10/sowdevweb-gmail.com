package com.projet.hpd.service;

import com.projet.hpd.domain.TypeNotif;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeNotif}.
 */
public interface TypeNotifService {

    /**
     * Save a typeNotif.
     *
     * @param typeNotif the entity to save.
     * @return the persisted entity.
     */
    TypeNotif save(TypeNotif typeNotif);

    /**
     * Get all the typeNotifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeNotif> findAll(Pageable pageable);

    /**
     * Get the "id" typeNotif.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeNotif> findOne(Long id);

    /**
     * Delete the "id" typeNotif.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
