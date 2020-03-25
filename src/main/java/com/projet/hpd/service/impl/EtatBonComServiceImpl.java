package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtatBonComService;
import com.projet.hpd.domain.EtatBonCom;
import com.projet.hpd.repository.EtatBonComRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatBonCom}.
 */
@Service
@Transactional
public class EtatBonComServiceImpl implements EtatBonComService {

    private final Logger log = LoggerFactory.getLogger(EtatBonComServiceImpl.class);

    private final EtatBonComRepository etatBonComRepository;

    public EtatBonComServiceImpl(EtatBonComRepository etatBonComRepository) {
        this.etatBonComRepository = etatBonComRepository;
    }

    /**
     * Save a etatBonCom.
     *
     * @param etatBonCom the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtatBonCom save(EtatBonCom etatBonCom) {
        log.debug("Request to save EtatBonCom : {}", etatBonCom);
        return etatBonComRepository.save(etatBonCom);
    }

    /**
     * Get all the etatBonComs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatBonCom> findAll(Pageable pageable) {
        log.debug("Request to get all EtatBonComs");
        return etatBonComRepository.findAll(pageable);
    }

    /**
     * Get one etatBonCom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatBonCom> findOne(Long id) {
        log.debug("Request to get EtatBonCom : {}", id);
        return etatBonComRepository.findById(id);
    }

    /**
     * Delete the etatBonCom by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatBonCom : {}", id);
        etatBonComRepository.deleteById(id);
    }
}
