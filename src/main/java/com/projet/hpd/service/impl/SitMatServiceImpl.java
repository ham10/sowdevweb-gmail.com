package com.projet.hpd.service.impl;

import com.projet.hpd.service.SitMatService;
import com.projet.hpd.domain.SitMat;
import com.projet.hpd.repository.SitMatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SitMat}.
 */
@Service
@Transactional
public class SitMatServiceImpl implements SitMatService {

    private final Logger log = LoggerFactory.getLogger(SitMatServiceImpl.class);

    private final SitMatRepository sitMatRepository;

    public SitMatServiceImpl(SitMatRepository sitMatRepository) {
        this.sitMatRepository = sitMatRepository;
    }

    /**
     * Save a sitMat.
     *
     * @param sitMat the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SitMat save(SitMat sitMat) {
        log.debug("Request to save SitMat : {}", sitMat);
        return sitMatRepository.save(sitMat);
    }

    /**
     * Get all the sitMats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SitMat> findAll(Pageable pageable) {
        log.debug("Request to get all SitMats");
        return sitMatRepository.findAll(pageable);
    }

    /**
     * Get one sitMat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SitMat> findOne(Long id) {
        log.debug("Request to get SitMat : {}", id);
        return sitMatRepository.findById(id);
    }

    /**
     * Delete the sitMat by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SitMat : {}", id);
        sitMatRepository.deleteById(id);
    }
}
