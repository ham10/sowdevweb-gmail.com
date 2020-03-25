package com.projet.hpd.service;

import com.projet.hpd.domain.BonLivraison;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BonLivraison}.
 */
public interface BonLivraisonService {

    /**
     * Save a bonLivraison.
     *
     * @param bonLivraison the entity to save.
     * @return the persisted entity.
     */
    BonLivraison save(BonLivraison bonLivraison);

    /**
     * Get all the bonLivraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BonLivraison> findAll(Pageable pageable);
    /**
     * Get all the BonLivraisonDTO where BonDeCommande is {@code null}.
     *
     * @return the list of entities.
     */
    List<BonLivraison> findAllWhereBonDeCommandeIsNull();

    /**
     * Get the "id" bonLivraison.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BonLivraison> findOne(Long id);

    /**
     * Delete the "id" bonLivraison.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
