package com.projet.hpd.service.impl;

import com.projet.hpd.service.SchemaComptaService;
import com.projet.hpd.domain.SchemaCompta;
import com.projet.hpd.repository.SchemaComptaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SchemaCompta}.
 */
@Service
@Transactional
public class SchemaComptaServiceImpl implements SchemaComptaService {

    private final Logger log = LoggerFactory.getLogger(SchemaComptaServiceImpl.class);

    private final SchemaComptaRepository schemaComptaRepository;

    public SchemaComptaServiceImpl(SchemaComptaRepository schemaComptaRepository) {
        this.schemaComptaRepository = schemaComptaRepository;
    }

    /**
     * Save a schemaCompta.
     *
     * @param schemaCompta the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SchemaCompta save(SchemaCompta schemaCompta) {
        log.debug("Request to save SchemaCompta : {}", schemaCompta);
        return schemaComptaRepository.save(schemaCompta);
    }

    /**
     * Get all the schemaComptas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchemaCompta> findAll(Pageable pageable) {
        log.debug("Request to get all SchemaComptas");
        return schemaComptaRepository.findAll(pageable);
    }

    /**
     * Get one schemaCompta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SchemaCompta> findOne(Long id) {
        log.debug("Request to get SchemaCompta : {}", id);
        return schemaComptaRepository.findById(id);
    }

    /**
     * Delete the schemaCompta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchemaCompta : {}", id);
        schemaComptaRepository.deleteById(id);
    }
}
