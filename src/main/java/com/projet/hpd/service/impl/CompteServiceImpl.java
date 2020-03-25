package com.projet.hpd.service.impl;

import com.projet.hpd.service.CompteService;
import com.projet.hpd.domain.Compte;
import com.projet.hpd.repository.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Compte}.
 */
@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final Logger log = LoggerFactory.getLogger(CompteServiceImpl.class);

    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * Save a compte.
     *
     * @param compte the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Compte save(Compte compte) {
        log.debug("Request to save Compte : {}", compte);
        return compteRepository.save(compte);
    }

    /**
     * Get all the comptes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Compte> findAll(Pageable pageable) {
        log.debug("Request to get all Comptes");
        return compteRepository.findAll(pageable);
    }

    /**
     * Get one compte by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Compte> findOne(Long id) {
        log.debug("Request to get Compte : {}", id);
        return compteRepository.findById(id);
    }

    /**
     * Delete the compte by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compte : {}", id);
        compteRepository.deleteById(id);
    }
}
