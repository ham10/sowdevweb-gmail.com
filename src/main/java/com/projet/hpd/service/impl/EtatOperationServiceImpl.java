package com.projet.hpd.service.impl;

import com.projet.hpd.service.EtatOperationService;
import com.projet.hpd.domain.EtatOperation;
import com.projet.hpd.repository.EtatOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EtatOperation}.
 */
@Service
@Transactional
public class EtatOperationServiceImpl implements EtatOperationService {

    private final Logger log = LoggerFactory.getLogger(EtatOperationServiceImpl.class);

    private final EtatOperationRepository etatOperationRepository;

    public EtatOperationServiceImpl(EtatOperationRepository etatOperationRepository) {
        this.etatOperationRepository = etatOperationRepository;
    }

    /**
     * Save a etatOperation.
     *
     * @param etatOperation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EtatOperation save(EtatOperation etatOperation) {
        log.debug("Request to save EtatOperation : {}", etatOperation);
        return etatOperationRepository.save(etatOperation);
    }

    /**
     * Get all the etatOperations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatOperation> findAll(Pageable pageable) {
        log.debug("Request to get all EtatOperations");
        return etatOperationRepository.findAll(pageable);
    }

    /**
     * Get one etatOperation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatOperation> findOne(Long id) {
        log.debug("Request to get EtatOperation : {}", id);
        return etatOperationRepository.findById(id);
    }

    /**
     * Delete the etatOperation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatOperation : {}", id);
        etatOperationRepository.deleteById(id);
    }
}
