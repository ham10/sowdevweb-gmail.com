package com.projet.hpd.service.impl;

import com.projet.hpd.service.ProdFournisService;
import com.projet.hpd.domain.ProdFournis;
import com.projet.hpd.repository.ProdFournisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProdFournis}.
 */
@Service
@Transactional
public class ProdFournisServiceImpl implements ProdFournisService {

    private final Logger log = LoggerFactory.getLogger(ProdFournisServiceImpl.class);

    private final ProdFournisRepository prodFournisRepository;

    public ProdFournisServiceImpl(ProdFournisRepository prodFournisRepository) {
        this.prodFournisRepository = prodFournisRepository;
    }

    /**
     * Save a prodFournis.
     *
     * @param prodFournis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProdFournis save(ProdFournis prodFournis) {
        log.debug("Request to save ProdFournis : {}", prodFournis);
        return prodFournisRepository.save(prodFournis);
    }

    /**
     * Get all the prodFournis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProdFournis> findAll(Pageable pageable) {
        log.debug("Request to get all ProdFournis");
        return prodFournisRepository.findAll(pageable);
    }

    /**
     * Get one prodFournis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProdFournis> findOne(Long id) {
        log.debug("Request to get ProdFournis : {}", id);
        return prodFournisRepository.findById(id);
    }

    /**
     * Delete the prodFournis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProdFournis : {}", id);
        prodFournisRepository.deleteById(id);
    }
}
