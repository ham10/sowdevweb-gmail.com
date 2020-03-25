package com.projet.hpd.service.impl;

import com.projet.hpd.service.TauxDeviseService;
import com.projet.hpd.domain.TauxDevise;
import com.projet.hpd.repository.TauxDeviseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TauxDevise}.
 */
@Service
@Transactional
public class TauxDeviseServiceImpl implements TauxDeviseService {

    private final Logger log = LoggerFactory.getLogger(TauxDeviseServiceImpl.class);

    private final TauxDeviseRepository tauxDeviseRepository;

    public TauxDeviseServiceImpl(TauxDeviseRepository tauxDeviseRepository) {
        this.tauxDeviseRepository = tauxDeviseRepository;
    }

    /**
     * Save a tauxDevise.
     *
     * @param tauxDevise the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TauxDevise save(TauxDevise tauxDevise) {
        log.debug("Request to save TauxDevise : {}", tauxDevise);
        return tauxDeviseRepository.save(tauxDevise);
    }

    /**
     * Get all the tauxDevises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TauxDevise> findAll(Pageable pageable) {
        log.debug("Request to get all TauxDevises");
        return tauxDeviseRepository.findAll(pageable);
    }

    /**
     * Get one tauxDevise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TauxDevise> findOne(Long id) {
        log.debug("Request to get TauxDevise : {}", id);
        return tauxDeviseRepository.findById(id);
    }

    /**
     * Delete the tauxDevise by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TauxDevise : {}", id);
        tauxDeviseRepository.deleteById(id);
    }
}
