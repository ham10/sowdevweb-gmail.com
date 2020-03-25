package com.projet.hpd.service;

import com.projet.hpd.domain.ParamSys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ParamSys}.
 */
public interface ParamSysService {

    /**
     * Save a paramSys.
     *
     * @param paramSys the entity to save.
     * @return the persisted entity.
     */
    ParamSys save(ParamSys paramSys);

    /**
     * Get all the paramSys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParamSys> findAll(Pageable pageable);

    /**
     * Get the "id" paramSys.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParamSys> findOne(Long id);

    /**
     * Delete the "id" paramSys.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
