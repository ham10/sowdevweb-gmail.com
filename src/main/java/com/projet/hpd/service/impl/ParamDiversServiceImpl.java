package com.projet.hpd.service.impl;

import com.projet.hpd.service.ParamDiversService;
import com.projet.hpd.domain.ParamDivers;
import com.projet.hpd.repository.ParamDiversRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ParamDivers}.
 */
@Service
@Transactional
public class ParamDiversServiceImpl implements ParamDiversService {

    private final Logger log = LoggerFactory.getLogger(ParamDiversServiceImpl.class);

    private final ParamDiversRepository paramDiversRepository;

    public ParamDiversServiceImpl(ParamDiversRepository paramDiversRepository) {
        this.paramDiversRepository = paramDiversRepository;
    }

    /**
     * Save a paramDivers.
     *
     * @param paramDivers the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParamDivers save(ParamDivers paramDivers) {
        log.debug("Request to save ParamDivers : {}", paramDivers);
        return paramDiversRepository.save(paramDivers);
    }

    /**
     * Get all the paramDivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParamDivers> findAll(Pageable pageable) {
        log.debug("Request to get all ParamDivers");
        return paramDiversRepository.findAll(pageable);
    }

    /**
     * Get one paramDivers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParamDivers> findOne(Long id) {
        log.debug("Request to get ParamDivers : {}", id);
        return paramDiversRepository.findById(id);
    }

    /**
     * Delete the paramDivers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParamDivers : {}", id);
        paramDiversRepository.deleteById(id);
    }
}
