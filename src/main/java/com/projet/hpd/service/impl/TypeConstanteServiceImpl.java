package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeConstanteService;
import com.projet.hpd.domain.TypeConstante;
import com.projet.hpd.repository.TypeConstanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeConstante}.
 */
@Service
@Transactional
public class TypeConstanteServiceImpl implements TypeConstanteService {

    private final Logger log = LoggerFactory.getLogger(TypeConstanteServiceImpl.class);

    private final TypeConstanteRepository typeConstanteRepository;

    public TypeConstanteServiceImpl(TypeConstanteRepository typeConstanteRepository) {
        this.typeConstanteRepository = typeConstanteRepository;
    }

    /**
     * Save a typeConstante.
     *
     * @param typeConstante the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeConstante save(TypeConstante typeConstante) {
        log.debug("Request to save TypeConstante : {}", typeConstante);
        return typeConstanteRepository.save(typeConstante);
    }

    /**
     * Get all the typeConstantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeConstante> findAll(Pageable pageable) {
        log.debug("Request to get all TypeConstantes");
        return typeConstanteRepository.findAll(pageable);
    }

    /**
     * Get one typeConstante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeConstante> findOne(Long id) {
        log.debug("Request to get TypeConstante : {}", id);
        return typeConstanteRepository.findById(id);
    }

    /**
     * Delete the typeConstante by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeConstante : {}", id);
        typeConstanteRepository.deleteById(id);
    }
}
