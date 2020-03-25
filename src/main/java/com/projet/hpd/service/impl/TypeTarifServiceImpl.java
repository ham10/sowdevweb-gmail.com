package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeTarifService;
import com.projet.hpd.domain.TypeTarif;
import com.projet.hpd.repository.TypeTarifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeTarif}.
 */
@Service
@Transactional
public class TypeTarifServiceImpl implements TypeTarifService {

    private final Logger log = LoggerFactory.getLogger(TypeTarifServiceImpl.class);

    private final TypeTarifRepository typeTarifRepository;

    public TypeTarifServiceImpl(TypeTarifRepository typeTarifRepository) {
        this.typeTarifRepository = typeTarifRepository;
    }

    /**
     * Save a typeTarif.
     *
     * @param typeTarif the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeTarif save(TypeTarif typeTarif) {
        log.debug("Request to save TypeTarif : {}", typeTarif);
        return typeTarifRepository.save(typeTarif);
    }

    /**
     * Get all the typeTarifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeTarif> findAll(Pageable pageable) {
        log.debug("Request to get all TypeTarifs");
        return typeTarifRepository.findAll(pageable);
    }

    /**
     * Get one typeTarif by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeTarif> findOne(Long id) {
        log.debug("Request to get TypeTarif : {}", id);
        return typeTarifRepository.findById(id);
    }

    /**
     * Delete the typeTarif by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeTarif : {}", id);
        typeTarifRepository.deleteById(id);
    }
}
