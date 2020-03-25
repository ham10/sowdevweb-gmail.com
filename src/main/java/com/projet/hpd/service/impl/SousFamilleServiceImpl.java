package com.projet.hpd.service.impl;

import com.projet.hpd.service.SousFamilleService;
import com.projet.hpd.domain.SousFamille;
import com.projet.hpd.repository.SousFamilleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SousFamille}.
 */
@Service
@Transactional
public class SousFamilleServiceImpl implements SousFamilleService {

    private final Logger log = LoggerFactory.getLogger(SousFamilleServiceImpl.class);

    private final SousFamilleRepository sousFamilleRepository;

    public SousFamilleServiceImpl(SousFamilleRepository sousFamilleRepository) {
        this.sousFamilleRepository = sousFamilleRepository;
    }

    /**
     * Save a sousFamille.
     *
     * @param sousFamille the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SousFamille save(SousFamille sousFamille) {
        log.debug("Request to save SousFamille : {}", sousFamille);
        return sousFamilleRepository.save(sousFamille);
    }

    /**
     * Get all the sousFamilles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SousFamille> findAll(Pageable pageable) {
        log.debug("Request to get all SousFamilles");
        return sousFamilleRepository.findAll(pageable);
    }

    /**
     * Get one sousFamille by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SousFamille> findOne(Long id) {
        log.debug("Request to get SousFamille : {}", id);
        return sousFamilleRepository.findById(id);
    }

    /**
     * Delete the sousFamille by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SousFamille : {}", id);
        sousFamilleRepository.deleteById(id);
    }
}
