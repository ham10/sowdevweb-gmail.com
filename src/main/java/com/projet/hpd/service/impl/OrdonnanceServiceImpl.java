package com.projet.hpd.service.impl;

import com.projet.hpd.service.OrdonnanceService;
import com.projet.hpd.domain.Ordonnance;
import com.projet.hpd.repository.OrdonnanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ordonnance}.
 */
@Service
@Transactional
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final Logger log = LoggerFactory.getLogger(OrdonnanceServiceImpl.class);

    private final OrdonnanceRepository ordonnanceRepository;

    public OrdonnanceServiceImpl(OrdonnanceRepository ordonnanceRepository) {
        this.ordonnanceRepository = ordonnanceRepository;
    }

    /**
     * Save a ordonnance.
     *
     * @param ordonnance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Ordonnance save(Ordonnance ordonnance) {
        log.debug("Request to save Ordonnance : {}", ordonnance);
        return ordonnanceRepository.save(ordonnance);
    }

    /**
     * Get all the ordonnances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ordonnance> findAll(Pageable pageable) {
        log.debug("Request to get all Ordonnances");
        return ordonnanceRepository.findAll(pageable);
    }

    /**
     * Get one ordonnance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Ordonnance> findOne(Long id) {
        log.debug("Request to get Ordonnance : {}", id);
        return ordonnanceRepository.findById(id);
    }

    /**
     * Delete the ordonnance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordonnance : {}", id);
        ordonnanceRepository.deleteById(id);
    }
}
