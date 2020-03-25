package com.projet.hpd.service.impl;

import com.projet.hpd.service.RDVService;
import com.projet.hpd.domain.RDV;
import com.projet.hpd.repository.RDVRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RDV}.
 */
@Service
@Transactional
public class RDVServiceImpl implements RDVService {

    private final Logger log = LoggerFactory.getLogger(RDVServiceImpl.class);

    private final RDVRepository rDVRepository;

    public RDVServiceImpl(RDVRepository rDVRepository) {
        this.rDVRepository = rDVRepository;
    }

    /**
     * Save a rDV.
     *
     * @param rDV the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RDV save(RDV rDV) {
        log.debug("Request to save RDV : {}", rDV);
        return rDVRepository.save(rDV);
    }

    /**
     * Get all the rDVS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RDV> findAll(Pageable pageable) {
        log.debug("Request to get all RDVS");
        return rDVRepository.findAll(pageable);
    }

    /**
     * Get one rDV by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RDV> findOne(Long id) {
        log.debug("Request to get RDV : {}", id);
        return rDVRepository.findById(id);
    }

    /**
     * Delete the rDV by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RDV : {}", id);
        rDVRepository.deleteById(id);
    }
}
