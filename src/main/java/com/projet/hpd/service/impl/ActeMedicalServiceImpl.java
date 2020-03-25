package com.projet.hpd.service.impl;

import com.projet.hpd.service.ActeMedicalService;
import com.projet.hpd.domain.ActeMedical;
import com.projet.hpd.repository.ActeMedicalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActeMedical}.
 */
@Service
@Transactional
public class ActeMedicalServiceImpl implements ActeMedicalService {

    private final Logger log = LoggerFactory.getLogger(ActeMedicalServiceImpl.class);

    private final ActeMedicalRepository acteMedicalRepository;

    public ActeMedicalServiceImpl(ActeMedicalRepository acteMedicalRepository) {
        this.acteMedicalRepository = acteMedicalRepository;
    }

    /**
     * Save a acteMedical.
     *
     * @param acteMedical the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ActeMedical save(ActeMedical acteMedical) {
        log.debug("Request to save ActeMedical : {}", acteMedical);
        return acteMedicalRepository.save(acteMedical);
    }

    /**
     * Get all the acteMedicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActeMedical> findAll(Pageable pageable) {
        log.debug("Request to get all ActeMedicals");
        return acteMedicalRepository.findAll(pageable);
    }

    /**
     * Get one acteMedical by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActeMedical> findOne(Long id) {
        log.debug("Request to get ActeMedical : {}", id);
        return acteMedicalRepository.findById(id);
    }

    /**
     * Delete the acteMedical by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActeMedical : {}", id);
        acteMedicalRepository.deleteById(id);
    }
}
