package com.projet.hpd.service.impl;

import com.projet.hpd.service.UniteService;
import com.projet.hpd.domain.Unite;
import com.projet.hpd.repository.UniteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Unite}.
 */
@Service
@Transactional
public class UniteServiceImpl implements UniteService {

    private final Logger log = LoggerFactory.getLogger(UniteServiceImpl.class);

    private final UniteRepository uniteRepository;

    public UniteServiceImpl(UniteRepository uniteRepository) {
        this.uniteRepository = uniteRepository;
    }

    /**
     * Save a unite.
     *
     * @param unite the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Unite save(Unite unite) {
        log.debug("Request to save Unite : {}", unite);
        return uniteRepository.save(unite);
    }

    /**
     * Get all the unites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Unite> findAll(Pageable pageable) {
        log.debug("Request to get all Unites");
        return uniteRepository.findAll(pageable);
    }

    /**
     * Get one unite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Unite> findOne(Long id) {
        log.debug("Request to get Unite : {}", id);
        return uniteRepository.findById(id);
    }

    /**
     * Delete the unite by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Unite : {}", id);
        uniteRepository.deleteById(id);
    }
}
