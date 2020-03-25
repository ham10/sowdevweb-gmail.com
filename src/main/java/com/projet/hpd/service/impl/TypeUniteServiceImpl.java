package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeUniteService;
import com.projet.hpd.domain.TypeUnite;
import com.projet.hpd.repository.TypeUniteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeUnite}.
 */
@Service
@Transactional
public class TypeUniteServiceImpl implements TypeUniteService {

    private final Logger log = LoggerFactory.getLogger(TypeUniteServiceImpl.class);

    private final TypeUniteRepository typeUniteRepository;

    public TypeUniteServiceImpl(TypeUniteRepository typeUniteRepository) {
        this.typeUniteRepository = typeUniteRepository;
    }

    /**
     * Save a typeUnite.
     *
     * @param typeUnite the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeUnite save(TypeUnite typeUnite) {
        log.debug("Request to save TypeUnite : {}", typeUnite);
        return typeUniteRepository.save(typeUnite);
    }

    /**
     * Get all the typeUnites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeUnite> findAll(Pageable pageable) {
        log.debug("Request to get all TypeUnites");
        return typeUniteRepository.findAll(pageable);
    }

    /**
     * Get one typeUnite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeUnite> findOne(Long id) {
        log.debug("Request to get TypeUnite : {}", id);
        return typeUniteRepository.findById(id);
    }

    /**
     * Delete the typeUnite by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeUnite : {}", id);
        typeUniteRepository.deleteById(id);
    }
}
