package com.projet.hpd.service.impl;

import com.projet.hpd.service.AyantDroitService;
import com.projet.hpd.domain.AyantDroit;
import com.projet.hpd.repository.AyantDroitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AyantDroit}.
 */
@Service
@Transactional
public class AyantDroitServiceImpl implements AyantDroitService {

    private final Logger log = LoggerFactory.getLogger(AyantDroitServiceImpl.class);

    private final AyantDroitRepository ayantDroitRepository;

    public AyantDroitServiceImpl(AyantDroitRepository ayantDroitRepository) {
        this.ayantDroitRepository = ayantDroitRepository;
    }

    /**
     * Save a ayantDroit.
     *
     * @param ayantDroit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AyantDroit save(AyantDroit ayantDroit) {
        log.debug("Request to save AyantDroit : {}", ayantDroit);
        return ayantDroitRepository.save(ayantDroit);
    }

    /**
     * Get all the ayantDroits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AyantDroit> findAll(Pageable pageable) {
        log.debug("Request to get all AyantDroits");
        return ayantDroitRepository.findAll(pageable);
    }

    /**
     * Get one ayantDroit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AyantDroit> findOne(Long id) {
        log.debug("Request to get AyantDroit : {}", id);
        return ayantDroitRepository.findById(id);
    }

    /**
     * Delete the ayantDroit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AyantDroit : {}", id);
        ayantDroitRepository.deleteById(id);
    }
}
