package com.projet.hpd.service.impl;

import com.projet.hpd.service.PaysService;
import com.projet.hpd.domain.Pays;
import com.projet.hpd.repository.PaysRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pays}.
 */
@Service
@Transactional
public class PaysServiceImpl implements PaysService {

    private final Logger log = LoggerFactory.getLogger(PaysServiceImpl.class);

    private final PaysRepository paysRepository;

    public PaysServiceImpl(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    /**
     * Save a pays.
     *
     * @param pays the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pays save(Pays pays) {
        log.debug("Request to save Pays : {}", pays);
        return paysRepository.save(pays);
    }

    /**
     * Get all the pays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pays> findAll(Pageable pageable) {
        log.debug("Request to get all Pays");
        return paysRepository.findAll(pageable);
    }

    /**
     * Get one pays by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pays> findOne(Long id) {
        log.debug("Request to get Pays : {}", id);
        return paysRepository.findById(id);
    }

    /**
     * Delete the pays by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pays : {}", id);
        paysRepository.deleteById(id);
    }
}
