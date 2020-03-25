package com.projet.hpd.service.impl;

import com.projet.hpd.service.OffreService;
import com.projet.hpd.domain.Offre;
import com.projet.hpd.repository.OffreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Offre}.
 */
@Service
@Transactional
public class OffreServiceImpl implements OffreService {

    private final Logger log = LoggerFactory.getLogger(OffreServiceImpl.class);

    private final OffreRepository offreRepository;

    public OffreServiceImpl(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    /**
     * Save a offre.
     *
     * @param offre the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Offre save(Offre offre) {
        log.debug("Request to save Offre : {}", offre);
        return offreRepository.save(offre);
    }

    /**
     * Get all the offres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Offre> findAll(Pageable pageable) {
        log.debug("Request to get all Offres");
        return offreRepository.findAll(pageable);
    }

    /**
     * Get one offre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Offre> findOne(Long id) {
        log.debug("Request to get Offre : {}", id);
        return offreRepository.findById(id);
    }

    /**
     * Delete the offre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offre : {}", id);
        offreRepository.deleteById(id);
    }
}
