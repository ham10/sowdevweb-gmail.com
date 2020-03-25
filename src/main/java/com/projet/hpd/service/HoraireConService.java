package com.projet.hpd.service;

import com.projet.hpd.domain.HoraireCon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link HoraireCon}.
 */
public interface HoraireConService {

    /**
     * Save a horaireCon.
     *
     * @param horaireCon the entity to save.
     * @return the persisted entity.
     */
    HoraireCon save(HoraireCon horaireCon);

    /**
     * Get all the horaireCons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HoraireCon> findAll(Pageable pageable);

    /**
     * Get the "id" horaireCon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HoraireCon> findOne(Long id);

    /**
     * Delete the "id" horaireCon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
