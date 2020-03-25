package com.projet.hpd.service;

import com.projet.hpd.domain.TypeCaisse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeCaisse}.
 */
public interface TypeCaisseService {

    /**
     * Save a typeCaisse.
     *
     * @param typeCaisse the entity to save.
     * @return the persisted entity.
     */
    TypeCaisse save(TypeCaisse typeCaisse);

    /**
     * Get all the typeCaisses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeCaisse> findAll(Pageable pageable);

    /**
     * Get the "id" typeCaisse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeCaisse> findOne(Long id);

    /**
     * Delete the "id" typeCaisse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
