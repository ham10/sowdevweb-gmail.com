package com.projet.hpd.service.impl;

import com.projet.hpd.service.PoleService;
import com.projet.hpd.domain.Pole;
import com.projet.hpd.repository.PoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pole}.
 */
@Service
@Transactional
public class PoleServiceImpl implements PoleService {

    private final Logger log = LoggerFactory.getLogger(PoleServiceImpl.class);

    private final PoleRepository poleRepository;

    public PoleServiceImpl(PoleRepository poleRepository) {
        this.poleRepository = poleRepository;
    }

    /**
     * Save a pole.
     *
     * @param pole the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pole save(Pole pole) {
        log.debug("Request to save Pole : {}", pole);
        return poleRepository.save(pole);
    }

    /**
     * Get all the poles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pole> findAll(Pageable pageable) {
        log.debug("Request to get all Poles");
        return poleRepository.findAll(pageable);
    }

    /**
     * Get one pole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pole> findOne(Long id) {
        log.debug("Request to get Pole : {}", id);
        return poleRepository.findById(id);
    }

    /**
     * Delete the pole by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pole : {}", id);
        poleRepository.deleteById(id);
    }
}
