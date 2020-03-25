package com.projet.hpd.service.impl;

import com.projet.hpd.service.DeviseService;
import com.projet.hpd.domain.Devise;
import com.projet.hpd.repository.DeviseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Devise}.
 */
@Service
@Transactional
public class DeviseServiceImpl implements DeviseService {

    private final Logger log = LoggerFactory.getLogger(DeviseServiceImpl.class);

    private final DeviseRepository deviseRepository;

    public DeviseServiceImpl(DeviseRepository deviseRepository) {
        this.deviseRepository = deviseRepository;
    }

    /**
     * Save a devise.
     *
     * @param devise the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Devise save(Devise devise) {
        log.debug("Request to save Devise : {}", devise);
        return deviseRepository.save(devise);
    }

    /**
     * Get all the devises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Devise> findAll(Pageable pageable) {
        log.debug("Request to get all Devises");
        return deviseRepository.findAll(pageable);
    }

    /**
     * Get one devise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Devise> findOne(Long id) {
        log.debug("Request to get Devise : {}", id);
        return deviseRepository.findById(id);
    }

    /**
     * Delete the devise by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Devise : {}", id);
        deviseRepository.deleteById(id);
    }
}
