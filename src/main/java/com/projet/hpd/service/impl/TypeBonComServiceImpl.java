package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeBonComService;
import com.projet.hpd.domain.TypeBonCom;
import com.projet.hpd.repository.TypeBonComRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeBonCom}.
 */
@Service
@Transactional
public class TypeBonComServiceImpl implements TypeBonComService {

    private final Logger log = LoggerFactory.getLogger(TypeBonComServiceImpl.class);

    private final TypeBonComRepository typeBonComRepository;

    public TypeBonComServiceImpl(TypeBonComRepository typeBonComRepository) {
        this.typeBonComRepository = typeBonComRepository;
    }

    /**
     * Save a typeBonCom.
     *
     * @param typeBonCom the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeBonCom save(TypeBonCom typeBonCom) {
        log.debug("Request to save TypeBonCom : {}", typeBonCom);
        return typeBonComRepository.save(typeBonCom);
    }

    /**
     * Get all the typeBonComs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeBonCom> findAll(Pageable pageable) {
        log.debug("Request to get all TypeBonComs");
        return typeBonComRepository.findAll(pageable);
    }

    /**
     * Get one typeBonCom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeBonCom> findOne(Long id) {
        log.debug("Request to get TypeBonCom : {}", id);
        return typeBonComRepository.findById(id);
    }

    /**
     * Delete the typeBonCom by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeBonCom : {}", id);
        typeBonComRepository.deleteById(id);
    }
}
