package com.projet.hpd.service.impl;

import com.projet.hpd.service.DetailPlanningService;
import com.projet.hpd.domain.DetailPlanning;
import com.projet.hpd.repository.DetailPlanningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DetailPlanning}.
 */
@Service
@Transactional
public class DetailPlanningServiceImpl implements DetailPlanningService {

    private final Logger log = LoggerFactory.getLogger(DetailPlanningServiceImpl.class);

    private final DetailPlanningRepository detailPlanningRepository;

    public DetailPlanningServiceImpl(DetailPlanningRepository detailPlanningRepository) {
        this.detailPlanningRepository = detailPlanningRepository;
    }

    /**
     * Save a detailPlanning.
     *
     * @param detailPlanning the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DetailPlanning save(DetailPlanning detailPlanning) {
        log.debug("Request to save DetailPlanning : {}", detailPlanning);
        return detailPlanningRepository.save(detailPlanning);
    }

    /**
     * Get all the detailPlannings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailPlanning> findAll(Pageable pageable) {
        log.debug("Request to get all DetailPlannings");
        return detailPlanningRepository.findAll(pageable);
    }

    /**
     * Get one detailPlanning by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetailPlanning> findOne(Long id) {
        log.debug("Request to get DetailPlanning : {}", id);
        return detailPlanningRepository.findById(id);
    }

    /**
     * Delete the detailPlanning by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailPlanning : {}", id);
        detailPlanningRepository.deleteById(id);
    }
}
