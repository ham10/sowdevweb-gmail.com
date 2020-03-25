package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypePlanningService;
import com.projet.hpd.domain.TypePlanning;
import com.projet.hpd.repository.TypePlanningRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypePlanning}.
 */
@Service
@Transactional
public class TypePlanningServiceImpl implements TypePlanningService {

    private final Logger log = LoggerFactory.getLogger(TypePlanningServiceImpl.class);

    private final TypePlanningRepository typePlanningRepository;

    public TypePlanningServiceImpl(TypePlanningRepository typePlanningRepository) {
        this.typePlanningRepository = typePlanningRepository;
    }

    /**
     * Save a typePlanning.
     *
     * @param typePlanning the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypePlanning save(TypePlanning typePlanning) {
        log.debug("Request to save TypePlanning : {}", typePlanning);
        return typePlanningRepository.save(typePlanning);
    }

    /**
     * Get all the typePlannings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePlanning> findAll(Pageable pageable) {
        log.debug("Request to get all TypePlannings");
        return typePlanningRepository.findAll(pageable);
    }

    /**
     * Get one typePlanning by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePlanning> findOne(Long id) {
        log.debug("Request to get TypePlanning : {}", id);
        return typePlanningRepository.findById(id);
    }

    /**
     * Delete the typePlanning by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePlanning : {}", id);
        typePlanningRepository.deleteById(id);
    }
}
