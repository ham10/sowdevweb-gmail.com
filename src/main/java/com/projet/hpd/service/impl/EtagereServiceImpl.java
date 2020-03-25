package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtagereService;
import com.projet.hpd.domain.Etagere;
import com.projet.hpd.repository.EtagereRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Etagere}.
 */
@Service
@Transactional
public class EtagereServiceImpl implements EtagereService {

    private final Logger log = LoggerFactory.getLogger(EtagereServiceImpl.class);

    private final EtagereRepository etagereRepository;

    public EtagereServiceImpl(EtagereRepository etagereRepository) {
        this.etagereRepository = etagereRepository;
    }

    /**
     * Save a etagere.
     *
     * @param etagere the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Etagere save(Etagere etagere) {
        log.debug("Request to save Etagere : {}", etagere);
        return etagereRepository.save(etagere);
    }

    /**
     * Get all the etageres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Etagere> findAll(Pageable pageable) {
        log.debug("Request to get all Etageres");
        return etagereRepository.findAll(pageable);
    }

    /**
     * Get one etagere by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Etagere> findOne(Long id) {
        log.debug("Request to get Etagere : {}", id);
        return etagereRepository.findById(id);
    }

    /**
     * Delete the etagere by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etagere : {}", id);
        etagereRepository.deleteById(id);
    }
}
