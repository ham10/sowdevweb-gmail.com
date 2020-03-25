package com.projet.hpd.service.impl;

import com.projet.hpd.service.ReponseService;
import com.projet.hpd.domain.Reponse;
import com.projet.hpd.repository.ReponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reponse}.
 */
@Service
@Transactional
public class ReponseServiceImpl implements ReponseService {

    private final Logger log = LoggerFactory.getLogger(ReponseServiceImpl.class);

    private final ReponseRepository reponseRepository;

    public ReponseServiceImpl(ReponseRepository reponseRepository) {
        this.reponseRepository = reponseRepository;
    }

    /**
     * Save a reponse.
     *
     * @param reponse the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Reponse save(Reponse reponse) {
        log.debug("Request to save Reponse : {}", reponse);
        return reponseRepository.save(reponse);
    }

    /**
     * Get all the reponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reponse> findAll(Pageable pageable) {
        log.debug("Request to get all Reponses");
        return reponseRepository.findAll(pageable);
    }

    /**
     * Get one reponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reponse> findOne(Long id) {
        log.debug("Request to get Reponse : {}", id);
        return reponseRepository.findById(id);
    }

    /**
     * Delete the reponse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reponse : {}", id);
        reponseRepository.deleteById(id);
    }
}
