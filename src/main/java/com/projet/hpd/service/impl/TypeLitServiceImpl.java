package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeLitService;
import com.projet.hpd.domain.TypeLit;
import com.projet.hpd.repository.TypeLitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeLit}.
 */
@Service
@Transactional
public class TypeLitServiceImpl implements TypeLitService {

    private final Logger log = LoggerFactory.getLogger(TypeLitServiceImpl.class);

    private final TypeLitRepository typeLitRepository;

    public TypeLitServiceImpl(TypeLitRepository typeLitRepository) {
        this.typeLitRepository = typeLitRepository;
    }

    /**
     * Save a typeLit.
     *
     * @param typeLit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeLit save(TypeLit typeLit) {
        log.debug("Request to save TypeLit : {}", typeLit);
        return typeLitRepository.save(typeLit);
    }

    /**
     * Get all the typeLits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeLit> findAll(Pageable pageable) {
        log.debug("Request to get all TypeLits");
        return typeLitRepository.findAll(pageable);
    }

    /**
     * Get one typeLit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeLit> findOne(Long id) {
        log.debug("Request to get TypeLit : {}", id);
        return typeLitRepository.findById(id);
    }

    /**
     * Delete the typeLit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeLit : {}", id);
        typeLitRepository.deleteById(id);
    }
}
