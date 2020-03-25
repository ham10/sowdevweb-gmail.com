package com.projet.hpd.service.impl;

import com.projet.hpd.service.CiviliteService;
import com.projet.hpd.domain.Civilite;
import com.projet.hpd.repository.CiviliteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Civilite}.
 */
@Service
@Transactional
public class CiviliteServiceImpl implements CiviliteService {

    private final Logger log = LoggerFactory.getLogger(CiviliteServiceImpl.class);

    private final CiviliteRepository civiliteRepository;

    public CiviliteServiceImpl(CiviliteRepository civiliteRepository) {
        this.civiliteRepository = civiliteRepository;
    }

    /**
     * Save a civilite.
     *
     * @param civilite the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Civilite save(Civilite civilite) {
        log.debug("Request to save Civilite : {}", civilite);
        return civiliteRepository.save(civilite);
    }

    /**
     * Get all the civilites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Civilite> findAll(Pageable pageable) {
        log.debug("Request to get all Civilites");
        return civiliteRepository.findAll(pageable);
    }

    /**
     * Get one civilite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Civilite> findOne(Long id) {
        log.debug("Request to get Civilite : {}", id);
        return civiliteRepository.findById(id);
    }

    /**
     * Delete the civilite by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Civilite : {}", id);
        civiliteRepository.deleteById(id);
    }
}
