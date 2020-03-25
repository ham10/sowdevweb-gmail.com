package com.projet.hpd.service;

import com.projet.hpd.domain.ParamCode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ParamCode}.
 */
public interface ParamCodeService {

    /**
     * Save a paramCode.
     *
     * @param paramCode the entity to save.
     * @return the persisted entity.
     */
    ParamCode save(ParamCode paramCode);

    /**
     * Get all the paramCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParamCode> findAll(Pageable pageable);

    /**
     * Get the "id" paramCode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParamCode> findOne(Long id);

    /**
     * Delete the "id" paramCode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
