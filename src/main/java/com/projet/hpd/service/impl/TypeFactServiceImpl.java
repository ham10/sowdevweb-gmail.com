package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeFactService;
import com.projet.hpd.domain.TypeFact;
import com.projet.hpd.repository.TypeFactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeFact}.
 */
@Service
@Transactional
public class TypeFactServiceImpl implements TypeFactService {

    private final Logger log = LoggerFactory.getLogger(TypeFactServiceImpl.class);

    private final TypeFactRepository typeFactRepository;

    public TypeFactServiceImpl(TypeFactRepository typeFactRepository) {
        this.typeFactRepository = typeFactRepository;
    }

    /**
     * Save a typeFact.
     *
     * @param typeFact the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeFact save(TypeFact typeFact) {
        log.debug("Request to save TypeFact : {}", typeFact);
        return typeFactRepository.save(typeFact);
    }

    /**
     * Get all the typeFacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeFact> findAll(Pageable pageable) {
        log.debug("Request to get all TypeFacts");
        return typeFactRepository.findAll(pageable);
    }

    /**
     * Get one typeFact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeFact> findOne(Long id) {
        log.debug("Request to get TypeFact : {}", id);
        return typeFactRepository.findById(id);
    }

    /**
     * Delete the typeFact by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeFact : {}", id);
        typeFactRepository.deleteById(id);
    }
}
