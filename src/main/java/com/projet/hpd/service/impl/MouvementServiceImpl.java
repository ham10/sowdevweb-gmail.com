package com.projet.hpd.service.impl;

import com.projet.hpd.service.MouvementService;
import com.projet.hpd.domain.Mouvement;
import com.projet.hpd.repository.MouvementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mouvement}.
 */
@Service
@Transactional
public class MouvementServiceImpl implements MouvementService {

    private final Logger log = LoggerFactory.getLogger(MouvementServiceImpl.class);

    private final MouvementRepository mouvementRepository;

    public MouvementServiceImpl(MouvementRepository mouvementRepository) {
        this.mouvementRepository = mouvementRepository;
    }

    /**
     * Save a mouvement.
     *
     * @param mouvement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Mouvement save(Mouvement mouvement) {
        log.debug("Request to save Mouvement : {}", mouvement);
        return mouvementRepository.save(mouvement);
    }

    /**
     * Get all the mouvements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Mouvement> findAll(Pageable pageable) {
        log.debug("Request to get all Mouvements");
        return mouvementRepository.findAll(pageable);
    }

    /**
     * Get one mouvement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Mouvement> findOne(Long id) {
        log.debug("Request to get Mouvement : {}", id);
        return mouvementRepository.findById(id);
    }

    /**
     * Delete the mouvement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mouvement : {}", id);
        mouvementRepository.deleteById(id);
    }
}
