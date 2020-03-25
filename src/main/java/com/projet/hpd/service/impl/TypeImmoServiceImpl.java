package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeImmoService;
import com.projet.hpd.domain.TypeImmo;
import com.projet.hpd.repository.TypeImmoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeImmo}.
 */
@Service
@Transactional
public class TypeImmoServiceImpl implements TypeImmoService {

    private final Logger log = LoggerFactory.getLogger(TypeImmoServiceImpl.class);

    private final TypeImmoRepository typeImmoRepository;

    public TypeImmoServiceImpl(TypeImmoRepository typeImmoRepository) {
        this.typeImmoRepository = typeImmoRepository;
    }

    /**
     * Save a typeImmo.
     *
     * @param typeImmo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeImmo save(TypeImmo typeImmo) {
        log.debug("Request to save TypeImmo : {}", typeImmo);
        return typeImmoRepository.save(typeImmo);
    }

    /**
     * Get all the typeImmos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeImmo> findAll(Pageable pageable) {
        log.debug("Request to get all TypeImmos");
        return typeImmoRepository.findAll(pageable);
    }

    /**
     * Get one typeImmo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeImmo> findOne(Long id) {
        log.debug("Request to get TypeImmo : {}", id);
        return typeImmoRepository.findById(id);
    }

    /**
     * Delete the typeImmo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeImmo : {}", id);
        typeImmoRepository.deleteById(id);
    }
}
