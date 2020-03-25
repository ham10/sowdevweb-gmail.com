package com.projet.hpd.service;

import com.projet.hpd.domain.TypeBonCom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeBonCom}.
 */
public interface TypeBonComService {

    /**
     * Save a typeBonCom.
     *
     * @param typeBonCom the entity to save.
     * @return the persisted entity.
     */
    TypeBonCom save(TypeBonCom typeBonCom);

    /**
     * Get all the typeBonComs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeBonCom> findAll(Pageable pageable);

    /**
     * Get the "id" typeBonCom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeBonCom> findOne(Long id);

    /**
     * Delete the "id" typeBonCom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
