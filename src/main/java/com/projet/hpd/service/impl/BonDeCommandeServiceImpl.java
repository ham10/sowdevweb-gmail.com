package com.projet.hpd.service.impl;

import com.projet.hpd.service.BonDeCommandeService;
import com.projet.hpd.domain.BonDeCommande;
import com.projet.hpd.repository.BonDeCommandeRepository;
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
 * Service Implementation for managing {@link BonDeCommande}.
 */
@Service
@Transactional
public class BonDeCommandeServiceImpl implements BonDeCommandeService {

    private final Logger log = LoggerFactory.getLogger(BonDeCommandeServiceImpl.class);

    private final BonDeCommandeRepository bonDeCommandeRepository;

    public BonDeCommandeServiceImpl(BonDeCommandeRepository bonDeCommandeRepository) {
        this.bonDeCommandeRepository = bonDeCommandeRepository;
    }

    /**
     * Save a bonDeCommande.
     *
     * @param bonDeCommande the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BonDeCommande save(BonDeCommande bonDeCommande) {
        log.debug("Request to save BonDeCommande : {}", bonDeCommande);
        return bonDeCommandeRepository.save(bonDeCommande);
    }

    /**
     * Get all the bonDeCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BonDeCommande> findAll(Pageable pageable) {
        log.debug("Request to get all BonDeCommandes");
        return bonDeCommandeRepository.findAll(pageable);
    }


    /**
     *  Get all the bonDeCommandes where Offre is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<BonDeCommande> findAllWhereOffreIsNull() {
        log.debug("Request to get all bonDeCommandes where Offre is null");
        return StreamSupport
            .stream(bonDeCommandeRepository.findAll().spliterator(), false)
            .filter(bonDeCommande -> bonDeCommande.getOffre() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one bonDeCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BonDeCommande> findOne(Long id) {
        log.debug("Request to get BonDeCommande : {}", id);
        return bonDeCommandeRepository.findById(id);
    }

    /**
     * Delete the bonDeCommande by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BonDeCommande : {}", id);
        bonDeCommandeRepository.deleteById(id);
    }
}
