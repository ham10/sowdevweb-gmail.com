package com.projet.hpd.service.impl;

import com.projet.hpd.service.PlanningService;
import com.projet.hpd.domain.Planning;
import com.projet.hpd.repository.PlanningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Planning}.
 */
@Service
@Transactional
public class PlanningServiceImpl implements PlanningService {

    private final Logger log = LoggerFactory.getLogger(PlanningServiceImpl.class);

    private final PlanningRepository planningRepository;

    public PlanningServiceImpl(PlanningRepository planningRepository) {
        this.planningRepository = planningRepository;
    }

    /**
     * Save a planning.
     *
     * @param planning the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Planning save(Planning planning) {
        log.debug("Request to save Planning : {}", planning);
        return planningRepository.save(planning);
    }

    /**
     * Get all the plannings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Planning> findAll(Pageable pageable) {
        log.debug("Request to get all Plannings");
        return planningRepository.findAll(pageable);
    }

    /**
     * Get one planning by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Planning> findOne(Long id) {
        log.debug("Request to get Planning : {}", id);
        return planningRepository.findById(id);
    }

    /**
     * Delete the planning by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Planning : {}", id);
        planningRepository.deleteById(id);
    }
}
