package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypePlatService;
import com.projet.hpd.domain.TypePlat;
import com.projet.hpd.repository.TypePlatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypePlat}.
 */
@Service
@Transactional
public class TypePlatServiceImpl implements TypePlatService {

    private final Logger log = LoggerFactory.getLogger(TypePlatServiceImpl.class);

    private final TypePlatRepository typePlatRepository;

    public TypePlatServiceImpl(TypePlatRepository typePlatRepository) {
        this.typePlatRepository = typePlatRepository;
    }

    /**
     * Save a typePlat.
     *
     * @param typePlat the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypePlat save(TypePlat typePlat) {
        log.debug("Request to save TypePlat : {}", typePlat);
        return typePlatRepository.save(typePlat);
    }

    /**
     * Get all the typePlats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePlat> findAll(Pageable pageable) {
        log.debug("Request to get all TypePlats");
        return typePlatRepository.findAll(pageable);
    }

    /**
     * Get one typePlat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePlat> findOne(Long id) {
        log.debug("Request to get TypePlat : {}", id);
        return typePlatRepository.findById(id);
    }

    /**
     * Delete the typePlat by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePlat : {}", id);
        typePlatRepository.deleteById(id);
    }
}
