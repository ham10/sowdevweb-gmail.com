package com.projet.hpd.service;

import com.projet.hpd.domain.TypePrCharge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypePrCharge}.
 */
public interface TypePrChargeService {

    /**
     * Save a typePrCharge.
     *
     * @param typePrCharge the entity to save.
     * @return the persisted entity.
     */
    TypePrCharge save(TypePrCharge typePrCharge);

    /**
     * Get all the typePrCharges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypePrCharge> findAll(Pageable pageable);

    /**
     * Get the "id" typePrCharge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypePrCharge> findOne(Long id);

    /**
     * Delete the "id" typePrCharge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
