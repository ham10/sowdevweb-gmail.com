package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtatRdvService;
import com.projet.hpd.domain.EtatRdv;
import com.projet.hpd.repository.EtatRdvRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatRdv}.
 */
@Service
@Transactional
public class EtatRdvServiceImpl implements EtatRdvService {

    private final Logger log = LoggerFactory.getLogger(EtatRdvServiceImpl.class);

    private final EtatRdvRepository etatRdvRepository;

    public EtatRdvServiceImpl(EtatRdvRepository etatRdvRepository) {
        this.etatRdvRepository = etatRdvRepository;
    }

    /**
     * Save a etatRdv.
     *
     * @param etatRdv the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtatRdv save(EtatRdv etatRdv) {
        log.debug("Request to save EtatRdv : {}", etatRdv);
        return etatRdvRepository.save(etatRdv);
    }

    /**
     * Get all the etatRdvs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatRdv> findAll(Pageable pageable) {
        log.debug("Request to get all EtatRdvs");
        return etatRdvRepository.findAll(pageable);
    }

    /**
     * Get one etatRdv by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatRdv> findOne(Long id) {
        log.debug("Request to get EtatRdv : {}", id);
        return etatRdvRepository.findById(id);
    }

    /**
     * Delete the etatRdv by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatRdv : {}", id);
        etatRdvRepository.deleteById(id);
    }
}
