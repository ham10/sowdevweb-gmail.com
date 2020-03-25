package com.projet.hpd.service.impl;

import com.projet.hpd.service.JourFerieService;
import com.projet.hpd.domain.JourFerie;
import com.projet.hpd.repository.JourFerieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JourFerie}.
 */
@Service
@Transactional
public class JourFerieServiceImpl implements JourFerieService {

    private final Logger log = LoggerFactory.getLogger(JourFerieServiceImpl.class);

    private final JourFerieRepository jourFerieRepository;

    public JourFerieServiceImpl(JourFerieRepository jourFerieRepository) {
        this.jourFerieRepository = jourFerieRepository;
    }

    /**
     * Save a jourFerie.
     *
     * @param jourFerie the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JourFerie save(JourFerie jourFerie) {
        log.debug("Request to save JourFerie : {}", jourFerie);
        return jourFerieRepository.save(jourFerie);
    }

    /**
     * Get all the jourFeries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JourFerie> findAll(Pageable pageable) {
        log.debug("Request to get all JourFeries");
        return jourFerieRepository.findAll(pageable);
    }

    /**
     * Get one jourFerie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JourFerie> findOne(Long id) {
        log.debug("Request to get JourFerie : {}", id);
        return jourFerieRepository.findById(id);
    }

    /**
     * Delete the jourFerie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourFerie : {}", id);
        jourFerieRepository.deleteById(id);
    }
}
