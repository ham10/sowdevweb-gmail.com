package com.projet.hpd.service.impl;

import com.projet.hpd.service.ImmoService;
import com.projet.hpd.domain.Immo;
import com.projet.hpd.repository.ImmoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Immo}.
 */
@Service
@Transactional
public class ImmoServiceImpl implements ImmoService {

    private final Logger log = LoggerFactory.getLogger(ImmoServiceImpl.class);

    private final ImmoRepository immoRepository;

    public ImmoServiceImpl(ImmoRepository immoRepository) {
        this.immoRepository = immoRepository;
    }

    /**
     * Save a immo.
     *
     * @param immo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Immo save(Immo immo) {
        log.debug("Request to save Immo : {}", immo);
        return immoRepository.save(immo);
    }

    /**
     * Get all the immos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Immo> findAll(Pageable pageable) {
        log.debug("Request to get all Immos");
        return immoRepository.findAll(pageable);
    }

    /**
     * Get one immo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Immo> findOne(Long id) {
        log.debug("Request to get Immo : {}", id);
        return immoRepository.findById(id);
    }

    /**
     * Delete the immo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Immo : {}", id);
        immoRepository.deleteById(id);
    }
}
