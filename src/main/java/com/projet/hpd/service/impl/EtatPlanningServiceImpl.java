package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtatPlanningService;
import com.projet.hpd.domain.EtatPlanning;
import com.projet.hpd.repository.EtatPlanningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatPlanning}.
 */
@Service
@Transactional
public class EtatPlanningServiceImpl implements EtatPlanningService {

    private final Logger log = LoggerFactory.getLogger(EtatPlanningServiceImpl.class);

    private final EtatPlanningRepository etatPlanningRepository;

    public EtatPlanningServiceImpl(EtatPlanningRepository etatPlanningRepository) {
        this.etatPlanningRepository = etatPlanningRepository;
    }

    /**
     * Save a etatPlanning.
     *
     * @param etatPlanning the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtatPlanning save(EtatPlanning etatPlanning) {
        log.debug("Request to save EtatPlanning : {}", etatPlanning);
        return etatPlanningRepository.save(etatPlanning);
    }

    /**
     * Get all the etatPlannings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatPlanning> findAll(Pageable pageable) {
        log.debug("Request to get all EtatPlannings");
        return etatPlanningRepository.findAll(pageable);
    }

    /**
     * Get one etatPlanning by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatPlanning> findOne(Long id) {
        log.debug("Request to get EtatPlanning : {}", id);
        return etatPlanningRepository.findById(id);
    }

    /**
     * Delete the etatPlanning by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatPlanning : {}", id);
        etatPlanningRepository.deleteById(id);
    }
}
