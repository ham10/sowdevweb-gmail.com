package com.projet.hpd.service.impl;

import com.projet.hpd.service.PlatService;
import com.projet.hpd.domain.Plat;
import com.projet.hpd.repository.PlatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Plat}.
 */
@Service
@Transactional
public class PlatServiceImpl implements PlatService {

    private final Logger log = LoggerFactory.getLogger(PlatServiceImpl.class);

    private final PlatRepository platRepository;

    public PlatServiceImpl(PlatRepository platRepository) {
        this.platRepository = platRepository;
    }

    /**
     * Save a plat.
     *
     * @param plat the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Plat save(Plat plat) {
        log.debug("Request to save Plat : {}", plat);
        return platRepository.save(plat);
    }

    /**
     * Get all the plats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Plat> findAll(Pageable pageable) {
        log.debug("Request to get all Plats");
        return platRepository.findAll(pageable);
    }

    /**
     * Get one plat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Plat> findOne(Long id) {
        log.debug("Request to get Plat : {}", id);
        return platRepository.findById(id);
    }

    /**
     * Delete the plat by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plat : {}", id);
        platRepository.deleteById(id);
    }
}
