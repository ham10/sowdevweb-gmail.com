package com.projet.hpd.service;

import com.projet.hpd.domain.TypeMvtStock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeMvtStock}.
 */
public interface TypeMvtStockService {

    /**
     * Save a typeMvtStock.
     *
     * @param typeMvtStock the entity to save.
     * @return the persisted entity.
     */
    TypeMvtStock save(TypeMvtStock typeMvtStock);

    /**
     * Get all the typeMvtStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeMvtStock> findAll(Pageable pageable);

    /**
     * Get the "id" typeMvtStock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeMvtStock> findOne(Long id);

    /**
     * Delete the "id" typeMvtStock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
