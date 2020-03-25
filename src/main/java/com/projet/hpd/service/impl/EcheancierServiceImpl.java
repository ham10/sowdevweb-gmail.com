package com.projet.hpd.service.impl;

import com.projet.hpd.service.EcheancierService;
import com.projet.hpd.domain.Echeancier;
import com.projet.hpd.repository.EcheancierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Echeancier}.
 */
@Service
@Transactional
public class EcheancierServiceImpl implements EcheancierService {

    private final Logger log = LoggerFactory.getLogger(EcheancierServiceImpl.class);

    private final EcheancierRepository echeancierRepository;

    public EcheancierServiceImpl(EcheancierRepository echeancierRepository) {
        this.echeancierRepository = echeancierRepository;
    }

    /**
     * Save a echeancier.
     *
     * @param echeancier the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Echeancier save(Echeancier echeancier) {
        log.debug("Request to save Echeancier : {}", echeancier);
        return echeancierRepository.save(echeancier);
    }

    /**
     * Get all the echeanciers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Echeancier> findAll(Pageable pageable) {
        log.debug("Request to get all Echeanciers");
        return echeancierRepository.findAll(pageable);
    }

    /**
     * Get one echeancier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Echeancier> findOne(Long id) {
        log.debug("Request to get Echeancier : {}", id);
        return echeancierRepository.findById(id);
    }

    /**
     * Delete the echeancier by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Echeancier : {}", id);
        echeancierRepository.deleteById(id);
    }
}
