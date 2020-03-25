package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypePoleService;
import com.projet.hpd.domain.TypePole;
import com.projet.hpd.repository.TypePoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypePole}.
 */
@Service
@Transactional
public class TypePoleServiceImpl implements TypePoleService {

    private final Logger log = LoggerFactory.getLogger(TypePoleServiceImpl.class);

    private final TypePoleRepository typePoleRepository;

    public TypePoleServiceImpl(TypePoleRepository typePoleRepository) {
        this.typePoleRepository = typePoleRepository;
    }

    /**
     * Save a typePole.
     *
     * @param typePole the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypePole save(TypePole typePole) {
        log.debug("Request to save TypePole : {}", typePole);
        return typePoleRepository.save(typePole);
    }

    /**
     * Get all the typePoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePole> findAll(Pageable pageable) {
        log.debug("Request to get all TypePoles");
        return typePoleRepository.findAll(pageable);
    }

    /**
     * Get one typePole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePole> findOne(Long id) {
        log.debug("Request to get TypePole : {}", id);
        return typePoleRepository.findById(id);
    }

    /**
     * Delete the typePole by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePole : {}", id);
        typePoleRepository.deleteById(id);
    }
}
