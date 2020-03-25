package com.projet.hpd.service.impl;

import com.projet.hpd.service.ClasseProdService;
import com.projet.hpd.domain.ClasseProd;
import com.projet.hpd.repository.ClasseProdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClasseProd}.
 */
@Service
@Transactional
public class ClasseProdServiceImpl implements ClasseProdService {

    private final Logger log = LoggerFactory.getLogger(ClasseProdServiceImpl.class);

    private final ClasseProdRepository classeProdRepository;

    public ClasseProdServiceImpl(ClasseProdRepository classeProdRepository) {
        this.classeProdRepository = classeProdRepository;
    }

    /**
     * Save a classeProd.
     *
     * @param classeProd the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClasseProd save(ClasseProd classeProd) {
        log.debug("Request to save ClasseProd : {}", classeProd);
        return classeProdRepository.save(classeProd);
    }

    /**
     * Get all the classeProds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClasseProd> findAll(Pageable pageable) {
        log.debug("Request to get all ClasseProds");
        return classeProdRepository.findAll(pageable);
    }

    /**
     * Get one classeProd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClasseProd> findOne(Long id) {
        log.debug("Request to get ClasseProd : {}", id);
        return classeProdRepository.findById(id);
    }

    /**
     * Delete the classeProd by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClasseProd : {}", id);
        classeProdRepository.deleteById(id);
    }
}
