package com.projet.hpd.service.impl;

import com.projet.hpd.service.NatureOpService;
import com.projet.hpd.domain.NatureOp;
import com.projet.hpd.repository.NatureOpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NatureOp}.
 */
@Service
@Transactional
public class NatureOpServiceImpl implements NatureOpService {

    private final Logger log = LoggerFactory.getLogger(NatureOpServiceImpl.class);

    private final NatureOpRepository natureOpRepository;

    public NatureOpServiceImpl(NatureOpRepository natureOpRepository) {
        this.natureOpRepository = natureOpRepository;
    }

    /**
     * Save a natureOp.
     *
     * @param natureOp the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NatureOp save(NatureOp natureOp) {
        log.debug("Request to save NatureOp : {}", natureOp);
        return natureOpRepository.save(natureOp);
    }

    /**
     * Get all the natureOps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NatureOp> findAll(Pageable pageable) {
        log.debug("Request to get all NatureOps");
        return natureOpRepository.findAll(pageable);
    }

    /**
     * Get one natureOp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NatureOp> findOne(Long id) {
        log.debug("Request to get NatureOp : {}", id);
        return natureOpRepository.findById(id);
    }

    /**
     * Delete the natureOp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureOp : {}", id);
        natureOpRepository.deleteById(id);
    }
}
