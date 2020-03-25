package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeFactureService;
import com.projet.hpd.domain.TypeFacture;
import com.projet.hpd.repository.TypeFactureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeFacture}.
 */
@Service
@Transactional
public class TypeFactureServiceImpl implements TypeFactureService {

    private final Logger log = LoggerFactory.getLogger(TypeFactureServiceImpl.class);

    private final TypeFactureRepository typeFactureRepository;

    public TypeFactureServiceImpl(TypeFactureRepository typeFactureRepository) {
        this.typeFactureRepository = typeFactureRepository;
    }

    /**
     * Save a typeFacture.
     *
     * @param typeFacture the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeFacture save(TypeFacture typeFacture) {
        log.debug("Request to save TypeFacture : {}", typeFacture);
        return typeFactureRepository.save(typeFacture);
    }

    /**
     * Get all the typeFactures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeFacture> findAll(Pageable pageable) {
        log.debug("Request to get all TypeFactures");
        return typeFactureRepository.findAll(pageable);
    }

    /**
     * Get one typeFacture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeFacture> findOne(Long id) {
        log.debug("Request to get TypeFacture : {}", id);
        return typeFactureRepository.findById(id);
    }

    /**
     * Delete the typeFacture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeFacture : {}", id);
        typeFactureRepository.deleteById(id);
    }
}
