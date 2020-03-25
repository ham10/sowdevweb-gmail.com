package com.projet.hpd.service.impl;

import com.projet.hpd.service.FicheMedicalService;
import com.projet.hpd.domain.FicheMedical;
import com.projet.hpd.repository.FicheMedicalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FicheMedical}.
 */
@Service
@Transactional
public class FicheMedicalServiceImpl implements FicheMedicalService {

    private final Logger log = LoggerFactory.getLogger(FicheMedicalServiceImpl.class);

    private final FicheMedicalRepository ficheMedicalRepository;

    public FicheMedicalServiceImpl(FicheMedicalRepository ficheMedicalRepository) {
        this.ficheMedicalRepository = ficheMedicalRepository;
    }

    /**
     * Save a ficheMedical.
     *
     * @param ficheMedical the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FicheMedical save(FicheMedical ficheMedical) {
        log.debug("Request to save FicheMedical : {}", ficheMedical);
        return ficheMedicalRepository.save(ficheMedical);
    }

    /**
     * Get all the ficheMedicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FicheMedical> findAll(Pageable pageable) {
        log.debug("Request to get all FicheMedicals");
        return ficheMedicalRepository.findAll(pageable);
    }

    /**
     * Get all the ficheMedicals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FicheMedical> findAllWithEagerRelationships(Pageable pageable) {
        return ficheMedicalRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one ficheMedical by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FicheMedical> findOne(Long id) {
        log.debug("Request to get FicheMedical : {}", id);
        return ficheMedicalRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the ficheMedical by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FicheMedical : {}", id);
        ficheMedicalRepository.deleteById(id);
    }
}
