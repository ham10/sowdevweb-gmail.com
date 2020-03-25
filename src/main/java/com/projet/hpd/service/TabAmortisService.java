package com.projet.hpd.service;

import com.projet.hpd.domain.TabAmortis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TabAmortis}.
 */
public interface TabAmortisService {

    /**
     * Save a tabAmortis.
     *
     * @param tabAmortis the entity to save.
     * @return the persisted entity.
     */
    TabAmortis save(TabAmortis tabAmortis);

    /**
     * Get all the tabAmortis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TabAmortis> findAll(Pageable pageable);

    /**
     * Get the "id" tabAmortis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TabAmortis> findOne(Long id);

    /**
     * Delete the "id" tabAmortis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
