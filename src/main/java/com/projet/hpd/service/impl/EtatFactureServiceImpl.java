package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtatFactureService;
import com.projet.hpd.domain.EtatFacture;
import com.projet.hpd.repository.EtatFactureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatFacture}.
 */
@Service
@Transactional
public class EtatFactureServiceImpl implements EtatFactureService {

    private final Logger log = LoggerFactory.getLogger(EtatFactureServiceImpl.class);

    private final EtatFactureRepository etatFactureRepository;

    public EtatFactureServiceImpl(EtatFactureRepository etatFactureRepository) {
        this.etatFactureRepository = etatFactureRepository;
    }

    /**
     * Save a etatFacture.
     *
     * @param etatFacture the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtatFacture save(EtatFacture etatFacture) {
        log.debug("Request to save EtatFacture : {}", etatFacture);
        return etatFactureRepository.save(etatFacture);
    }

    /**
     * Get all the etatFactures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatFacture> findAll(Pageable pageable) {
        log.debug("Request to get all EtatFactures");
        return etatFactureRepository.findAll(pageable);
    }

    /**
     * Get one etatFacture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatFacture> findOne(Long id) {
        log.debug("Request to get EtatFacture : {}", id);
        return etatFactureRepository.findById(id);
    }

    /**
     * Delete the etatFacture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatFacture : {}", id);
        etatFactureRepository.deleteById(id);
    }
}
