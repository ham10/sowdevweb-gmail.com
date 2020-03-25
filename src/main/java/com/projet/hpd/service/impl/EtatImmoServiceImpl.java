package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtatImmoService;
import com.projet.hpd.domain.EtatImmo;
import com.projet.hpd.repository.EtatImmoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatImmo}.
 */
@Service
@Transactional
public class EtatImmoServiceImpl implements EtatImmoService {

    private final Logger log = LoggerFactory.getLogger(EtatImmoServiceImpl.class);

    private final EtatImmoRepository etatImmoRepository;

    public EtatImmoServiceImpl(EtatImmoRepository etatImmoRepository) {
        this.etatImmoRepository = etatImmoRepository;
    }

    /**
     * Save a etatImmo.
     *
     * @param etatImmo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtatImmo save(EtatImmo etatImmo) {
        log.debug("Request to save EtatImmo : {}", etatImmo);
        return etatImmoRepository.save(etatImmo);
    }

    /**
     * Get all the etatImmos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatImmo> findAll(Pageable pageable) {
        log.debug("Request to get all EtatImmos");
        return etatImmoRepository.findAll(pageable);
    }

    /**
     * Get one etatImmo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatImmo> findOne(Long id) {
        log.debug("Request to get EtatImmo : {}", id);
        return etatImmoRepository.findById(id);
    }

    /**
     * Delete the etatImmo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatImmo : {}", id);
        etatImmoRepository.deleteById(id);
    }
}
