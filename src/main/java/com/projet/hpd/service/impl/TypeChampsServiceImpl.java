package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeChampsService;
import com.projet.hpd.domain.TypeChamps;
import com.projet.hpd.repository.TypeChampsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeChamps}.
 */
@Service
@Transactional
public class TypeChampsServiceImpl implements TypeChampsService {

    private final Logger log = LoggerFactory.getLogger(TypeChampsServiceImpl.class);

    private final TypeChampsRepository typeChampsRepository;

    public TypeChampsServiceImpl(TypeChampsRepository typeChampsRepository) {
        this.typeChampsRepository = typeChampsRepository;
    }

    /**
     * Save a typeChamps.
     *
     * @param typeChamps the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeChamps save(TypeChamps typeChamps) {
        log.debug("Request to save TypeChamps : {}", typeChamps);
        return typeChampsRepository.save(typeChamps);
    }

    /**
     * Get all the typeChamps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeChamps> findAll(Pageable pageable) {
        log.debug("Request to get all TypeChamps");
        return typeChampsRepository.findAll(pageable);
    }

    /**
     * Get one typeChamps by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeChamps> findOne(Long id) {
        log.debug("Request to get TypeChamps : {}", id);
        return typeChampsRepository.findById(id);
    }

    /**
     * Delete the typeChamps by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeChamps : {}", id);
        typeChampsRepository.deleteById(id);
    }
}
