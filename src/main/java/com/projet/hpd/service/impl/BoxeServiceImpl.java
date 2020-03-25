package com.projet.hpd.service.impl;

import com.projet.hpd.service.BoxeService;
import com.projet.hpd.domain.Boxe;
import com.projet.hpd.repository.BoxeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Boxe}.
 */
@Service
@Transactional
public class BoxeServiceImpl implements BoxeService {

    private final Logger log = LoggerFactory.getLogger(BoxeServiceImpl.class);

    private final BoxeRepository boxeRepository;

    public BoxeServiceImpl(BoxeRepository boxeRepository) {
        this.boxeRepository = boxeRepository;
    }

    /**
     * Save a boxe.
     *
     * @param boxe the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Boxe save(Boxe boxe) {
        log.debug("Request to save Boxe : {}", boxe);
        return boxeRepository.save(boxe);
    }

    /**
     * Get all the boxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Boxe> findAll(Pageable pageable) {
        log.debug("Request to get all Boxes");
        return boxeRepository.findAll(pageable);
    }

    /**
     * Get one boxe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Boxe> findOne(Long id) {
        log.debug("Request to get Boxe : {}", id);
        return boxeRepository.findById(id);
    }

    /**
     * Delete the boxe by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Boxe : {}", id);
        boxeRepository.deleteById(id);
    }
}
