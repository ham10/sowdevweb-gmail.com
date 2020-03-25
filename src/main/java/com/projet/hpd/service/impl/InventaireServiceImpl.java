package com.projet.hpd.service.impl;

import com.projet.hpd.service.InventaireService;
import com.projet.hpd.domain.Inventaire;
import com.projet.hpd.repository.InventaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Inventaire}.
 */
@Service
@Transactional
public class InventaireServiceImpl implements InventaireService {

    private final Logger log = LoggerFactory.getLogger(InventaireServiceImpl.class);

    private final InventaireRepository inventaireRepository;

    public InventaireServiceImpl(InventaireRepository inventaireRepository) {
        this.inventaireRepository = inventaireRepository;
    }

    /**
     * Save a inventaire.
     *
     * @param inventaire the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Inventaire save(Inventaire inventaire) {
        log.debug("Request to save Inventaire : {}", inventaire);
        return inventaireRepository.save(inventaire);
    }

    /**
     * Get all the inventaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Inventaire> findAll(Pageable pageable) {
        log.debug("Request to get all Inventaires");
        return inventaireRepository.findAll(pageable);
    }

    /**
     * Get one inventaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Inventaire> findOne(Long id) {
        log.debug("Request to get Inventaire : {}", id);
        return inventaireRepository.findById(id);
    }

    /**
     * Delete the inventaire by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inventaire : {}", id);
        inventaireRepository.deleteById(id);
    }
}
