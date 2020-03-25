package com.projet.hpd.service.impl;

import com.projet.hpd.service.JourService;
import com.projet.hpd.domain.Jour;
import com.projet.hpd.repository.JourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Jour}.
 */
@Service
@Transactional
public class JourServiceImpl implements JourService {

    private final Logger log = LoggerFactory.getLogger(JourServiceImpl.class);

    private final JourRepository jourRepository;

    public JourServiceImpl(JourRepository jourRepository) {
        this.jourRepository = jourRepository;
    }

    /**
     * Save a jour.
     *
     * @param jour the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Jour save(Jour jour) {
        log.debug("Request to save Jour : {}", jour);
        return jourRepository.save(jour);
    }

    /**
     * Get all the jours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Jour> findAll(Pageable pageable) {
        log.debug("Request to get all Jours");
        return jourRepository.findAll(pageable);
    }

    /**
     * Get one jour by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Jour> findOne(Long id) {
        log.debug("Request to get Jour : {}", id);
        return jourRepository.findById(id);
    }

    /**
     * Delete the jour by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Jour : {}", id);
        jourRepository.deleteById(id);
    }
}
