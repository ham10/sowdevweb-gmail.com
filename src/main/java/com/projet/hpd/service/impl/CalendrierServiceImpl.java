package com.projet.hpd.service.impl;

import com.projet.hpd.service.CalendrierService;
import com.projet.hpd.domain.Calendrier;
import com.projet.hpd.repository.CalendrierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Calendrier}.
 */
@Service
@Transactional
public class CalendrierServiceImpl implements CalendrierService {

    private final Logger log = LoggerFactory.getLogger(CalendrierServiceImpl.class);

    private final CalendrierRepository calendrierRepository;

    public CalendrierServiceImpl(CalendrierRepository calendrierRepository) {
        this.calendrierRepository = calendrierRepository;
    }

    /**
     * Save a calendrier.
     *
     * @param calendrier the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Calendrier save(Calendrier calendrier) {
        log.debug("Request to save Calendrier : {}", calendrier);
        return calendrierRepository.save(calendrier);
    }

    /**
     * Get all the calendriers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Calendrier> findAll(Pageable pageable) {
        log.debug("Request to get all Calendriers");
        return calendrierRepository.findAll(pageable);
    }

    /**
     * Get one calendrier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Calendrier> findOne(Long id) {
        log.debug("Request to get Calendrier : {}", id);
        return calendrierRepository.findById(id);
    }

    /**
     * Delete the calendrier by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calendrier : {}", id);
        calendrierRepository.deleteById(id);
    }
}
