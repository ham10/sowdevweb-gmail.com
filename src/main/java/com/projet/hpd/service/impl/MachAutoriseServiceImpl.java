package com.projet.hpd.service.impl;

import com.projet.hpd.service.MachAutoriseService;
import com.projet.hpd.domain.MachAutorise;
import com.projet.hpd.repository.MachAutoriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MachAutorise}.
 */
@Service
@Transactional
public class MachAutoriseServiceImpl implements MachAutoriseService {

    private final Logger log = LoggerFactory.getLogger(MachAutoriseServiceImpl.class);

    private final MachAutoriseRepository machAutoriseRepository;

    public MachAutoriseServiceImpl(MachAutoriseRepository machAutoriseRepository) {
        this.machAutoriseRepository = machAutoriseRepository;
    }

    /**
     * Save a machAutorise.
     *
     * @param machAutorise the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MachAutorise save(MachAutorise machAutorise) {
        log.debug("Request to save MachAutorise : {}", machAutorise);
        return machAutoriseRepository.save(machAutorise);
    }

    /**
     * Get all the machAutorises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MachAutorise> findAll(Pageable pageable) {
        log.debug("Request to get all MachAutorises");
        return machAutoriseRepository.findAll(pageable);
    }

    /**
     * Get one machAutorise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MachAutorise> findOne(Long id) {
        log.debug("Request to get MachAutorise : {}", id);
        return machAutoriseRepository.findById(id);
    }

    /**
     * Delete the machAutorise by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MachAutorise : {}", id);
        machAutoriseRepository.deleteById(id);
    }
}
