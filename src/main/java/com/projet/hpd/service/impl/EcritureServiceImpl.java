package com.projet.hpd.service.impl;

import com.projet.hpd.service.EcritureService;
import com.projet.hpd.domain.Ecriture;
import com.projet.hpd.repository.EcritureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ecriture}.
 */
@Service
@Transactional
public class EcritureServiceImpl implements EcritureService {

    private final Logger log = LoggerFactory.getLogger(EcritureServiceImpl.class);

    private final EcritureRepository ecritureRepository;

    public EcritureServiceImpl(EcritureRepository ecritureRepository) {
        this.ecritureRepository = ecritureRepository;
    }

    /**
     * Save a ecriture.
     *
     * @param ecriture the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Ecriture save(Ecriture ecriture) {
        log.debug("Request to save Ecriture : {}", ecriture);
        return ecritureRepository.save(ecriture);
    }

    /**
     * Get all the ecritures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ecriture> findAll(Pageable pageable) {
        log.debug("Request to get all Ecritures");
        return ecritureRepository.findAll(pageable);
    }

    /**
     * Get one ecriture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Ecriture> findOne(Long id) {
        log.debug("Request to get Ecriture : {}", id);
        return ecritureRepository.findById(id);
    }

    /**
     * Delete the ecriture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ecriture : {}", id);
        ecritureRepository.deleteById(id);
    }
}
