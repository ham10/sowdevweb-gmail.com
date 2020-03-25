package com.projet.hpd.service.impl;

import com.projet.hpd.service.CaisseService;
import com.projet.hpd.domain.Caisse;
import com.projet.hpd.repository.CaisseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Caisse}.
 */
@Service
@Transactional
public class CaisseServiceImpl implements CaisseService {

    private final Logger log = LoggerFactory.getLogger(CaisseServiceImpl.class);

    private final CaisseRepository caisseRepository;

    public CaisseServiceImpl(CaisseRepository caisseRepository) {
        this.caisseRepository = caisseRepository;
    }

    /**
     * Save a caisse.
     *
     * @param caisse the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Caisse save(Caisse caisse) {
        log.debug("Request to save Caisse : {}", caisse);
        return caisseRepository.save(caisse);
    }

    /**
     * Get all the caisses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Caisse> findAll(Pageable pageable) {
        log.debug("Request to get all Caisses");
        return caisseRepository.findAll(pageable);
    }

    /**
     * Get one caisse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Caisse> findOne(Long id) {
        log.debug("Request to get Caisse : {}", id);
        return caisseRepository.findById(id);
    }

    /**
     * Delete the caisse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caisse : {}", id);
        caisseRepository.deleteById(id);
    }
}
