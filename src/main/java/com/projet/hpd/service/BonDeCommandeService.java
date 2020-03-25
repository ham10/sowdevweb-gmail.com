package com.projet.hpd.service;

import com.projet.hpd.domain.BonDeCommande;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BonDeCommande}.
 */
public interface BonDeCommandeService {

    /**
     * Save a bonDeCommande.
     *
     * @param bonDeCommande the entity to save.
     * @return the persisted entity.
     */
    BonDeCommande save(BonDeCommande bonDeCommande);

    /**
     * Get all the bonDeCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BonDeCommande> findAll(Pageable pageable);
    /**
     * Get all the BonDeCommandeDTO where Offre is {@code null}.
     *
     * @return the list of entities.
     */
    List<BonDeCommande> findAllWhereOffreIsNull();

    /**
     * Get the "id" bonDeCommande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BonDeCommande> findOne(Long id);

    /**
     * Delete the "id" bonDeCommande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
