package com.projet.hpd.service.impl;

import com.projet.hpd.service.FormeProdService;
import com.projet.hpd.domain.FormeProd;
import com.projet.hpd.repository.FormeProdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FormeProd}.
 */
@Service
@Transactional
public class FormeProdServiceImpl implements FormeProdService {

    private final Logger log = LoggerFactory.getLogger(FormeProdServiceImpl.class);

    private final FormeProdRepository formeProdRepository;

    public FormeProdServiceImpl(FormeProdRepository formeProdRepository) {
        this.formeProdRepository = formeProdRepository;
    }

    /**
     * Save a formeProd.
     *
     * @param formeProd the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FormeProd save(FormeProd formeProd) {
        log.debug("Request to save FormeProd : {}", formeProd);
        return formeProdRepository.save(formeProd);
    }

    /**
     * Get all the formeProds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormeProd> findAll(Pageable pageable) {
        log.debug("Request to get all FormeProds");
        return formeProdRepository.findAll(pageable);
    }

    /**
     * Get one formeProd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormeProd> findOne(Long id) {
        log.debug("Request to get FormeProd : {}", id);
        return formeProdRepository.findById(id);
    }

    /**
     * Delete the formeProd by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormeProd : {}", id);
        formeProdRepository.deleteById(id);
    }
}
