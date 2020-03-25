package com.projet.hpd.service;

import com.projet.hpd.domain.LigneCommande;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link LigneCommande}.
 */
public interface LigneCommandeService {

    /**
     * Save a ligneCommande.
     *
     * @param ligneCommande the entity to save.
     * @return the persisted entity.
     */
    LigneCommande save(LigneCommande ligneCommande);

    /**
     * Get all the ligneCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LigneCommande> findAll(Pageable pageable);

    /**
     * Get the "id" ligneCommande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneCommande> findOne(Long id);

    /**
     * Delete the "id" ligneCommande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
