package com.projet.hpd.service.impl;

import com.projet.hpd.service.LigneLivraisonService;
import com.projet.hpd.domain.LigneLivraison;
import com.projet.hpd.repository.LigneLivraisonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LigneLivraison}.
 */
@Service
@Transactional
public class LigneLivraisonServiceImpl implements LigneLivraisonService {

    private final Logger log = LoggerFactory.getLogger(LigneLivraisonServiceImpl.class);

    private final LigneLivraisonRepository ligneLivraisonRepository;

    public LigneLivraisonServiceImpl(LigneLivraisonRepository ligneLivraisonRepository) {
        this.ligneLivraisonRepository = ligneLivraisonRepository;
    }

    /**
     * Save a ligneLivraison.
     *
     * @param ligneLivraison the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LigneLivraison save(LigneLivraison ligneLivraison) {
        log.debug("Request to save LigneLivraison : {}", ligneLivraison);
        return ligneLivraisonRepository.save(ligneLivraison);
    }

    /**
     * Get all the ligneLivraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LigneLivraison> findAll(Pageable pageable) {
        log.debug("Request to get all LigneLivraisons");
        return ligneLivraisonRepository.findAll(pageable);
    }

    /**
     * Get one ligneLivraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LigneLivraison> findOne(Long id) {
        log.debug("Request to get LigneLivraison : {}", id);
        return ligneLivraisonRepository.findById(id);
    }

    /**
     * Delete the ligneLivraison by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneLivraison : {}", id);
        ligneLivraisonRepository.deleteById(id);
    }
}
