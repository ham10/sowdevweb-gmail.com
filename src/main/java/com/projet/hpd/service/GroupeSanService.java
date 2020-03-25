package com.projet.hpd.service;

import com.projet.hpd.domain.GroupeSan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GroupeSan}.
 */
public interface GroupeSanService {

    /**
     * Save a groupeSan.
     *
     * @param groupeSan the entity to save.
     * @return the persisted entity.
     */
    GroupeSan save(GroupeSan groupeSan);

    /**
     * Get all the groupeSans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupeSan> findAll(Pageable pageable);

    /**
     * Get the "id" groupeSan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupeSan> findOne(Long id);

    /**
     * Delete the "id" groupeSan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
