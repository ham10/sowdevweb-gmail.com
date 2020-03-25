package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtablisService;
import com.projet.hpd.domain.Etablis;
import com.projet.hpd.repository.EtablisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Etablis}.
 */
@Service
@Transactional
public class EtablisServiceImpl implements EtablisService {

    private final Logger log = LoggerFactory.getLogger(EtablisServiceImpl.class);

    private final EtablisRepository etablisRepository;

    public EtablisServiceImpl(EtablisRepository etablisRepository) {
        this.etablisRepository = etablisRepository;
    }

    /**
     * Save a etablis.
     *
     * @param etablis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Etablis save(Etablis etablis) {
        log.debug("Request to save Etablis : {}", etablis);
        return etablisRepository.save(etablis);
    }

    /**
     * Get all the etablis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Etablis> findAll(Pageable pageable) {
        log.debug("Request to get all Etablis");
        return etablisRepository.findAll(pageable);
    }

    /**
     * Get one etablis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Etablis> findOne(Long id) {
        log.debug("Request to get Etablis : {}", id);
        return etablisRepository.findById(id);
    }

    /**
     * Delete the etablis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etablis : {}", id);
        etablisRepository.deleteById(id);
    }
}
