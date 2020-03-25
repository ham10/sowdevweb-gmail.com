package com.projet.hpd.service.impl;

import com.projet.hpd.service.SpecialiteService;
import com.projet.hpd.domain.Specialite;
import com.projet.hpd.repository.SpecialiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Specialite}.
 */
@Service
@Transactional
public class SpecialiteServiceImpl implements SpecialiteService {

    private final Logger log = LoggerFactory.getLogger(SpecialiteServiceImpl.class);

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteServiceImpl(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    /**
     * Save a specialite.
     *
     * @param specialite the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Specialite save(Specialite specialite) {
        log.debug("Request to save Specialite : {}", specialite);
        return specialiteRepository.save(specialite);
    }

    /**
     * Get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Specialite> findAll(Pageable pageable) {
        log.debug("Request to get all Specialites");
        return specialiteRepository.findAll(pageable);
    }

    /**
     * Get one specialite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Specialite> findOne(Long id) {
        log.debug("Request to get Specialite : {}", id);
        return specialiteRepository.findById(id);
    }

    /**
     * Delete the specialite by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Specialite : {}", id);
        specialiteRepository.deleteById(id);
    }
}
