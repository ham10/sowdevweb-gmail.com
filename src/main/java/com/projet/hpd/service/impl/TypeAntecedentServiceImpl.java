package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeAntecedentService;
import com.projet.hpd.domain.TypeAntecedent;
import com.projet.hpd.repository.TypeAntecedentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeAntecedent}.
 */
@Service
@Transactional
public class TypeAntecedentServiceImpl implements TypeAntecedentService {

    private final Logger log = LoggerFactory.getLogger(TypeAntecedentServiceImpl.class);

    private final TypeAntecedentRepository typeAntecedentRepository;

    public TypeAntecedentServiceImpl(TypeAntecedentRepository typeAntecedentRepository) {
        this.typeAntecedentRepository = typeAntecedentRepository;
    }

    /**
     * Save a typeAntecedent.
     *
     * @param typeAntecedent the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeAntecedent save(TypeAntecedent typeAntecedent) {
        log.debug("Request to save TypeAntecedent : {}", typeAntecedent);
        return typeAntecedentRepository.save(typeAntecedent);
    }

    /**
     * Get all the typeAntecedents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeAntecedent> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAntecedents");
        return typeAntecedentRepository.findAll(pageable);
    }

    /**
     * Get one typeAntecedent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeAntecedent> findOne(Long id) {
        log.debug("Request to get TypeAntecedent : {}", id);
        return typeAntecedentRepository.findById(id);
    }

    /**
     * Delete the typeAntecedent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeAntecedent : {}", id);
        typeAntecedentRepository.deleteById(id);
    }
}
