package com.projet.hpd.service.impl;

import com.projet.hpd.service.AntecedentService;
import com.projet.hpd.domain.Antecedent;
import com.projet.hpd.repository.AntecedentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Antecedent}.
 */
@Service
@Transactional
public class AntecedentServiceImpl implements AntecedentService {

    private final Logger log = LoggerFactory.getLogger(AntecedentServiceImpl.class);

    private final AntecedentRepository antecedentRepository;

    public AntecedentServiceImpl(AntecedentRepository antecedentRepository) {
        this.antecedentRepository = antecedentRepository;
    }

    /**
     * Save a antecedent.
     *
     * @param antecedent the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Antecedent save(Antecedent antecedent) {
        log.debug("Request to save Antecedent : {}", antecedent);
        return antecedentRepository.save(antecedent);
    }

    /**
     * Get all the antecedents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Antecedent> findAll(Pageable pageable) {
        log.debug("Request to get all Antecedents");
        return antecedentRepository.findAll(pageable);
    }

    /**
     * Get one antecedent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Antecedent> findOne(Long id) {
        log.debug("Request to get Antecedent : {}", id);
        return antecedentRepository.findById(id);
    }

    /**
     * Delete the antecedent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Antecedent : {}", id);
        antecedentRepository.deleteById(id);
    }
}
