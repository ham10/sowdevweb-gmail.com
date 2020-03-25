package com.projet.hpd.service;

import com.projet.hpd.domain.TypeFournis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeFournis}.
 */
public interface TypeFournisService {

    /**
     * Save a typeFournis.
     *
     * @param typeFournis the entity to save.
     * @return the persisted entity.
     */
    TypeFournis save(TypeFournis typeFournis);

    /**
     * Get all the typeFournis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeFournis> findAll(Pageable pageable);

    /**
     * Get the "id" typeFournis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeFournis> findOne(Long id);

    /**
     * Delete the "id" typeFournis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
