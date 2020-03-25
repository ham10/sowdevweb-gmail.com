package com.projet.hpd.service.impl;

import com.projet.hpd.service.DepotService;
import com.projet.hpd.domain.Depot;
import com.projet.hpd.repository.DepotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Depot}.
 */
@Service
@Transactional
public class DepotServiceImpl implements DepotService {

    private final Logger log = LoggerFactory.getLogger(DepotServiceImpl.class);

    private final DepotRepository depotRepository;

    public DepotServiceImpl(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    /**
     * Save a depot.
     *
     * @param depot the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Depot save(Depot depot) {
        log.debug("Request to save Depot : {}", depot);
        return depotRepository.save(depot);
    }

    /**
     * Get all the depots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Depot> findAll(Pageable pageable) {
        log.debug("Request to get all Depots");
        return depotRepository.findAll(pageable);
    }

    /**
     * Get one depot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Depot> findOne(Long id) {
        log.debug("Request to get Depot : {}", id);
        return depotRepository.findById(id);
    }

    /**
     * Delete the depot by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Depot : {}", id);
        depotRepository.deleteById(id);
    }
}
