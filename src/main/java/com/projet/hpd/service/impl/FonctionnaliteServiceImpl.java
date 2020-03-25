package com.projet.hpd.service.impl;

import com.projet.hpd.service.FonctionnaliteService;
import com.projet.hpd.domain.Fonctionnalite;
import com.projet.hpd.repository.FonctionnaliteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fonctionnalite}.
 */
@Service
@Transactional
public class FonctionnaliteServiceImpl implements FonctionnaliteService {

    private final Logger log = LoggerFactory.getLogger(FonctionnaliteServiceImpl.class);

    private final FonctionnaliteRepository fonctionnaliteRepository;

    public FonctionnaliteServiceImpl(FonctionnaliteRepository fonctionnaliteRepository) {
        this.fonctionnaliteRepository = fonctionnaliteRepository;
    }

    /**
     * Save a fonctionnalite.
     *
     * @param fonctionnalite the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Fonctionnalite save(Fonctionnalite fonctionnalite) {
        log.debug("Request to save Fonctionnalite : {}", fonctionnalite);
        return fonctionnaliteRepository.save(fonctionnalite);
    }

    /**
     * Get all the fonctionnalites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Fonctionnalite> findAll(Pageable pageable) {
        log.debug("Request to get all Fonctionnalites");
        return fonctionnaliteRepository.findAll(pageable);
    }

    /**
     * Get one fonctionnalite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Fonctionnalite> findOne(Long id) {
        log.debug("Request to get Fonctionnalite : {}", id);
        return fonctionnaliteRepository.findById(id);
    }

    /**
     * Delete the fonctionnalite by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fonctionnalite : {}", id);
        fonctionnaliteRepository.deleteById(id);
    }
}
