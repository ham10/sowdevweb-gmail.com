package com.projet.hpd.service.impl;

import com.projet.hpd.service.ParamCodeService;
import com.projet.hpd.domain.ParamCode;
import com.projet.hpd.repository.ParamCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ParamCode}.
 */
@Service
@Transactional
public class ParamCodeServiceImpl implements ParamCodeService {

    private final Logger log = LoggerFactory.getLogger(ParamCodeServiceImpl.class);

    private final ParamCodeRepository paramCodeRepository;

    public ParamCodeServiceImpl(ParamCodeRepository paramCodeRepository) {
        this.paramCodeRepository = paramCodeRepository;
    }

    /**
     * Save a paramCode.
     *
     * @param paramCode the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParamCode save(ParamCode paramCode) {
        log.debug("Request to save ParamCode : {}", paramCode);
        return paramCodeRepository.save(paramCode);
    }

    /**
     * Get all the paramCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParamCode> findAll(Pageable pageable) {
        log.debug("Request to get all ParamCodes");
        return paramCodeRepository.findAll(pageable);
    }

    /**
     * Get one paramCode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParamCode> findOne(Long id) {
        log.debug("Request to get ParamCode : {}", id);
        return paramCodeRepository.findById(id);
    }

    /**
     * Delete the paramCode by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParamCode : {}", id);
        paramCodeRepository.deleteById(id);
    }
}
