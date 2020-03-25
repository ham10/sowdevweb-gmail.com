package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeSoinsService;
import com.projet.hpd.domain.TypeSoins;
import com.projet.hpd.repository.TypeSoinsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeSoins}.
 */
@Service
@Transactional
public class TypeSoinsServiceImpl implements TypeSoinsService {

    private final Logger log = LoggerFactory.getLogger(TypeSoinsServiceImpl.class);

    private final TypeSoinsRepository typeSoinsRepository;

    public TypeSoinsServiceImpl(TypeSoinsRepository typeSoinsRepository) {
        this.typeSoinsRepository = typeSoinsRepository;
    }

    /**
     * Save a typeSoins.
     *
     * @param typeSoins the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeSoins save(TypeSoins typeSoins) {
        log.debug("Request to save TypeSoins : {}", typeSoins);
        return typeSoinsRepository.save(typeSoins);
    }

    /**
     * Get all the typeSoins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeSoins> findAll(Pageable pageable) {
        log.debug("Request to get all TypeSoins");
        return typeSoinsRepository.findAll(pageable);
    }

    /**
     * Get one typeSoins by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeSoins> findOne(Long id) {
        log.debug("Request to get TypeSoins : {}", id);
        return typeSoinsRepository.findById(id);
    }

    /**
     * Delete the typeSoins by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeSoins : {}", id);
        typeSoinsRepository.deleteById(id);
    }
}
