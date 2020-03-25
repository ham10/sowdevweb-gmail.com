package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypePlateauService;
import com.projet.hpd.domain.TypePlateau;
import com.projet.hpd.repository.TypePlateauRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypePlateau}.
 */
@Service
@Transactional
public class TypePlateauServiceImpl implements TypePlateauService {

    private final Logger log = LoggerFactory.getLogger(TypePlateauServiceImpl.class);

    private final TypePlateauRepository typePlateauRepository;

    public TypePlateauServiceImpl(TypePlateauRepository typePlateauRepository) {
        this.typePlateauRepository = typePlateauRepository;
    }

    /**
     * Save a typePlateau.
     *
     * @param typePlateau the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypePlateau save(TypePlateau typePlateau) {
        log.debug("Request to save TypePlateau : {}", typePlateau);
        return typePlateauRepository.save(typePlateau);
    }

    /**
     * Get all the typePlateaus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePlateau> findAll(Pageable pageable) {
        log.debug("Request to get all TypePlateaus");
        return typePlateauRepository.findAll(pageable);
    }

    /**
     * Get one typePlateau by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePlateau> findOne(Long id) {
        log.debug("Request to get TypePlateau : {}", id);
        return typePlateauRepository.findById(id);
    }

    /**
     * Delete the typePlateau by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePlateau : {}", id);
        typePlateauRepository.deleteById(id);
    }
}
