package com.projet.hpd.service.impl;

import com.projet.hpd.service.TarifService;
import com.projet.hpd.domain.Tarif;
import com.projet.hpd.repository.TarifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tarif}.
 */
@Service
@Transactional
public class TarifServiceImpl implements TarifService {

    private final Logger log = LoggerFactory.getLogger(TarifServiceImpl.class);

    private final TarifRepository tarifRepository;

    public TarifServiceImpl(TarifRepository tarifRepository) {
        this.tarifRepository = tarifRepository;
    }

    /**
     * Save a tarif.
     *
     * @param tarif the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Tarif save(Tarif tarif) {
        log.debug("Request to save Tarif : {}", tarif);
        return tarifRepository.save(tarif);
    }

    /**
     * Get all the tarifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tarif> findAll(Pageable pageable) {
        log.debug("Request to get all Tarifs");
        return tarifRepository.findAll(pageable);
    }

    /**
     * Get one tarif by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Tarif> findOne(Long id) {
        log.debug("Request to get Tarif : {}", id);
        return tarifRepository.findById(id);
    }

    /**
     * Delete the tarif by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarif : {}", id);
        tarifRepository.deleteById(id);
    }
}
