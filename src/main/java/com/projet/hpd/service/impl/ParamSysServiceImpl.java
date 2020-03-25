package com.projet.hpd.service.impl;

import com.projet.hpd.service.ParamSysService;
import com.projet.hpd.domain.ParamSys;
import com.projet.hpd.repository.ParamSysRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ParamSys}.
 */
@Service
@Transactional
public class ParamSysServiceImpl implements ParamSysService {

    private final Logger log = LoggerFactory.getLogger(ParamSysServiceImpl.class);

    private final ParamSysRepository paramSysRepository;

    public ParamSysServiceImpl(ParamSysRepository paramSysRepository) {
        this.paramSysRepository = paramSysRepository;
    }

    /**
     * Save a paramSys.
     *
     * @param paramSys the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParamSys save(ParamSys paramSys) {
        log.debug("Request to save ParamSys : {}", paramSys);
        return paramSysRepository.save(paramSys);
    }

    /**
     * Get all the paramSys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParamSys> findAll(Pageable pageable) {
        log.debug("Request to get all ParamSys");
        return paramSysRepository.findAll(pageable);
    }

    /**
     * Get one paramSys by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParamSys> findOne(Long id) {
        log.debug("Request to get ParamSys : {}", id);
        return paramSysRepository.findById(id);
    }

    /**
     * Delete the paramSys by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParamSys : {}", id);
        paramSysRepository.deleteById(id);
    }
}
