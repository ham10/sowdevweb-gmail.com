package com.projet.hpd.service.impl;

import com.projet.hpd.service.CompteGeneService;
import com.projet.hpd.domain.CompteGene;
import com.projet.hpd.repository.CompteGeneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompteGene}.
 */
@Service
@Transactional
public class CompteGeneServiceImpl implements CompteGeneService {

    private final Logger log = LoggerFactory.getLogger(CompteGeneServiceImpl.class);

    private final CompteGeneRepository compteGeneRepository;

    public CompteGeneServiceImpl(CompteGeneRepository compteGeneRepository) {
        this.compteGeneRepository = compteGeneRepository;
    }

    /**
     * Save a compteGene.
     *
     * @param compteGene the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompteGene save(CompteGene compteGene) {
        log.debug("Request to save CompteGene : {}", compteGene);
        return compteGeneRepository.save(compteGene);
    }

    /**
     * Get all the compteGenes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompteGene> findAll(Pageable pageable) {
        log.debug("Request to get all CompteGenes");
        return compteGeneRepository.findAll(pageable);
    }

    /**
     * Get one compteGene by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompteGene> findOne(Long id) {
        log.debug("Request to get CompteGene : {}", id);
        return compteGeneRepository.findById(id);
    }

    /**
     * Delete the compteGene by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompteGene : {}", id);
        compteGeneRepository.deleteById(id);
    }
}
