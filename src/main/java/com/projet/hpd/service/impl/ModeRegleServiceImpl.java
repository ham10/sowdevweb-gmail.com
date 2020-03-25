package com.projet.hpd.service.impl;

import com.projet.hpd.service.ModeRegleService;
import com.projet.hpd.domain.ModeRegle;
import com.projet.hpd.repository.ModeRegleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ModeRegle}.
 */
@Service
@Transactional
public class ModeRegleServiceImpl implements ModeRegleService {

    private final Logger log = LoggerFactory.getLogger(ModeRegleServiceImpl.class);

    private final ModeRegleRepository modeRegleRepository;

    public ModeRegleServiceImpl(ModeRegleRepository modeRegleRepository) {
        this.modeRegleRepository = modeRegleRepository;
    }

    /**
     * Save a modeRegle.
     *
     * @param modeRegle the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ModeRegle save(ModeRegle modeRegle) {
        log.debug("Request to save ModeRegle : {}", modeRegle);
        return modeRegleRepository.save(modeRegle);
    }

    /**
     * Get all the modeRegles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModeRegle> findAll(Pageable pageable) {
        log.debug("Request to get all ModeRegles");
        return modeRegleRepository.findAll(pageable);
    }

    /**
     * Get one modeRegle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModeRegle> findOne(Long id) {
        log.debug("Request to get ModeRegle : {}", id);
        return modeRegleRepository.findById(id);
    }

    /**
     * Delete the modeRegle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModeRegle : {}", id);
        modeRegleRepository.deleteById(id);
    }
}
