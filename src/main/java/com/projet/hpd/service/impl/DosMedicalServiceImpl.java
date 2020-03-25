package com.projet.hpd.service.impl;

import com.projet.hpd.service.DosMedicalService;
import com.projet.hpd.domain.DosMedical;
import com.projet.hpd.repository.DosMedicalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DosMedical}.
 */
@Service
@Transactional
public class DosMedicalServiceImpl implements DosMedicalService {

    private final Logger log = LoggerFactory.getLogger(DosMedicalServiceImpl.class);

    private final DosMedicalRepository dosMedicalRepository;

    public DosMedicalServiceImpl(DosMedicalRepository dosMedicalRepository) {
        this.dosMedicalRepository = dosMedicalRepository;
    }

    /**
     * Save a dosMedical.
     *
     * @param dosMedical the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DosMedical save(DosMedical dosMedical) {
        log.debug("Request to save DosMedical : {}", dosMedical);
        return dosMedicalRepository.save(dosMedical);
    }

    /**
     * Get all the dosMedicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DosMedical> findAll(Pageable pageable) {
        log.debug("Request to get all DosMedicals");
        return dosMedicalRepository.findAll(pageable);
    }

    /**
     * Get all the dosMedicals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DosMedical> findAllWithEagerRelationships(Pageable pageable) {
        return dosMedicalRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one dosMedical by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DosMedical> findOne(Long id) {
        log.debug("Request to get DosMedical : {}", id);
        return dosMedicalRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the dosMedical by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DosMedical : {}", id);
        dosMedicalRepository.deleteById(id);
    }
}
