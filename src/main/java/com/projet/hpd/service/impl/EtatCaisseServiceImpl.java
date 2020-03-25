package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtatCaisseService;
import com.projet.hpd.domain.EtatCaisse;
import com.projet.hpd.repository.EtatCaisseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatCaisse}.
 */
@Service
@Transactional
public class EtatCaisseServiceImpl implements EtatCaisseService {

    private final Logger log = LoggerFactory.getLogger(EtatCaisseServiceImpl.class);

    private final EtatCaisseRepository etatCaisseRepository;

    public EtatCaisseServiceImpl(EtatCaisseRepository etatCaisseRepository) {
        this.etatCaisseRepository = etatCaisseRepository;
    }

    /**
     * Save a etatCaisse.
     *
     * @param etatCaisse the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtatCaisse save(EtatCaisse etatCaisse) {
        log.debug("Request to save EtatCaisse : {}", etatCaisse);
        return etatCaisseRepository.save(etatCaisse);
    }

    /**
     * Get all the etatCaisses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatCaisse> findAll(Pageable pageable) {
        log.debug("Request to get all EtatCaisses");
        return etatCaisseRepository.findAll(pageable);
    }

    /**
     * Get one etatCaisse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatCaisse> findOne(Long id) {
        log.debug("Request to get EtatCaisse : {}", id);
        return etatCaisseRepository.findById(id);
    }

    /**
     * Delete the etatCaisse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatCaisse : {}", id);
        etatCaisseRepository.deleteById(id);
    }
}
