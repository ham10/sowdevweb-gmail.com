package com.projet.hpd.service.impl;

import com.projet.hpd.service.CibleService;
import com.projet.hpd.domain.Cible;
import com.projet.hpd.repository.CibleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cible}.
 */
@Service
@Transactional
public class CibleServiceImpl implements CibleService {

    private final Logger log = LoggerFactory.getLogger(CibleServiceImpl.class);

    private final CibleRepository cibleRepository;

    public CibleServiceImpl(CibleRepository cibleRepository) {
        this.cibleRepository = cibleRepository;
    }

    /**
     * Save a cible.
     *
     * @param cible the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Cible save(Cible cible) {
        log.debug("Request to save Cible : {}", cible);
        return cibleRepository.save(cible);
    }

    /**
     * Get all the cibles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cible> findAll(Pageable pageable) {
        log.debug("Request to get all Cibles");
        return cibleRepository.findAll(pageable);
    }

    /**
     * Get one cible by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cible> findOne(Long id) {
        log.debug("Request to get Cible : {}", id);
        return cibleRepository.findById(id);
    }

    /**
     * Delete the cible by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cible : {}", id);
        cibleRepository.deleteById(id);
    }
}
