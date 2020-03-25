package com.projet.hpd.service.impl;

import com.projet.hpd.service.FactureService;
import com.projet.hpd.domain.Facture;
import com.projet.hpd.repository.FactureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Facture}.
 */
@Service
@Transactional
public class FactureServiceImpl implements FactureService {

    private final Logger log = LoggerFactory.getLogger(FactureServiceImpl.class);

    private final FactureRepository factureRepository;

    public FactureServiceImpl(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    /**
     * Save a facture.
     *
     * @param facture the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Facture save(Facture facture) {
        log.debug("Request to save Facture : {}", facture);
        return factureRepository.save(facture);
    }

    /**
     * Get all the factures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Facture> findAll(Pageable pageable) {
        log.debug("Request to get all Factures");
        return factureRepository.findAll(pageable);
    }

    /**
     * Get one facture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Facture> findOne(Long id) {
        log.debug("Request to get Facture : {}", id);
        return factureRepository.findById(id);
    }

    /**
     * Delete the facture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Facture : {}", id);
        factureRepository.deleteById(id);
    }
}
