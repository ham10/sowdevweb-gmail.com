package com.projet.hpd.service.impl;

import com.projet.hpd.service.ResultatActeService;
import com.projet.hpd.domain.ResultatActe;
import com.projet.hpd.repository.ResultatActeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResultatActe}.
 */
@Service
@Transactional
public class ResultatActeServiceImpl implements ResultatActeService {

    private final Logger log = LoggerFactory.getLogger(ResultatActeServiceImpl.class);

    private final ResultatActeRepository resultatActeRepository;

    public ResultatActeServiceImpl(ResultatActeRepository resultatActeRepository) {
        this.resultatActeRepository = resultatActeRepository;
    }

    /**
     * Save a resultatActe.
     *
     * @param resultatActe the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ResultatActe save(ResultatActe resultatActe) {
        log.debug("Request to save ResultatActe : {}", resultatActe);
        return resultatActeRepository.save(resultatActe);
    }

    /**
     * Get all the resultatActes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResultatActe> findAll(Pageable pageable) {
        log.debug("Request to get all ResultatActes");
        return resultatActeRepository.findAll(pageable);
    }

    /**
     * Get all the resultatActes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ResultatActe> findAllWithEagerRelationships(Pageable pageable) {
        return resultatActeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one resultatActe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResultatActe> findOne(Long id) {
        log.debug("Request to get ResultatActe : {}", id);
        return resultatActeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the resultatActe by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResultatActe : {}", id);
        resultatActeRepository.deleteById(id);
    }
}
