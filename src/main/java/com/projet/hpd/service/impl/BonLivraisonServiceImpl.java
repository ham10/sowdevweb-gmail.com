package com.projet.hpd.service.impl;

import com.projet.hpd.service.BonLivraisonService;
import com.projet.hpd.domain.BonLivraison;
import com.projet.hpd.repository.BonLivraisonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link BonLivraison}.
 */
@Service
@Transactional
public class BonLivraisonServiceImpl implements BonLivraisonService {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonServiceImpl.class);

    private final BonLivraisonRepository bonLivraisonRepository;

    public BonLivraisonServiceImpl(BonLivraisonRepository bonLivraisonRepository) {
        this.bonLivraisonRepository = bonLivraisonRepository;
    }

    /**
     * Save a bonLivraison.
     *
     * @param bonLivraison the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BonLivraison save(BonLivraison bonLivraison) {
        log.debug("Request to save BonLivraison : {}", bonLivraison);
        return bonLivraisonRepository.save(bonLivraison);
    }

    /**
     * Get all the bonLivraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BonLivraison> findAll(Pageable pageable) {
        log.debug("Request to get all BonLivraisons");
        return bonLivraisonRepository.findAll(pageable);
    }


    /**
     *  Get all the bonLivraisons where BonDeCommande is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<BonLivraison> findAllWhereBonDeCommandeIsNull() {
        log.debug("Request to get all bonLivraisons where BonDeCommande is null");
        return StreamSupport
            .stream(bonLivraisonRepository.findAll().spliterator(), false)
            .filter(bonLivraison -> bonLivraison.getBonDeCommande() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one bonLivraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BonLivraison> findOne(Long id) {
        log.debug("Request to get BonLivraison : {}", id);
        return bonLivraisonRepository.findById(id);
    }

    /**
     * Delete the bonLivraison by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BonLivraison : {}", id);
        bonLivraisonRepository.deleteById(id);
    }
}
