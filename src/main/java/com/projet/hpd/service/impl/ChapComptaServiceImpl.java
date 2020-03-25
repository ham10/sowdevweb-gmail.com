package com.projet.hpd.service.impl;

import com.projet.hpd.service.ChapComptaService;
import com.projet.hpd.domain.ChapCompta;
import com.projet.hpd.repository.ChapComptaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ChapCompta}.
 */
@Service
@Transactional
public class ChapComptaServiceImpl implements ChapComptaService {

    private final Logger log = LoggerFactory.getLogger(ChapComptaServiceImpl.class);

    private final ChapComptaRepository chapComptaRepository;

    public ChapComptaServiceImpl(ChapComptaRepository chapComptaRepository) {
        this.chapComptaRepository = chapComptaRepository;
    }

    /**
     * Save a chapCompta.
     *
     * @param chapCompta the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ChapCompta save(ChapCompta chapCompta) {
        log.debug("Request to save ChapCompta : {}", chapCompta);
        return chapComptaRepository.save(chapCompta);
    }

    /**
     * Get all the chapComptas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChapCompta> findAll(Pageable pageable) {
        log.debug("Request to get all ChapComptas");
        return chapComptaRepository.findAll(pageable);
    }

    /**
     * Get one chapCompta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChapCompta> findOne(Long id) {
        log.debug("Request to get ChapCompta : {}", id);
        return chapComptaRepository.findById(id);
    }

    /**
     * Delete the chapCompta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChapCompta : {}", id);
        chapComptaRepository.deleteById(id);
    }
}
