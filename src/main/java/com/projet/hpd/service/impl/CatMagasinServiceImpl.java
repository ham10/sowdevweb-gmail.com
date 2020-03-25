package com.projet.hpd.service.impl;

import com.projet.hpd.service.CatMagasinService;
import com.projet.hpd.domain.CatMagasin;
import com.projet.hpd.repository.CatMagasinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatMagasin}.
 */
@Service
@Transactional
public class CatMagasinServiceImpl implements CatMagasinService {

    private final Logger log = LoggerFactory.getLogger(CatMagasinServiceImpl.class);

    private final CatMagasinRepository catMagasinRepository;

    public CatMagasinServiceImpl(CatMagasinRepository catMagasinRepository) {
        this.catMagasinRepository = catMagasinRepository;
    }

    /**
     * Save a catMagasin.
     *
     * @param catMagasin the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CatMagasin save(CatMagasin catMagasin) {
        log.debug("Request to save CatMagasin : {}", catMagasin);
        return catMagasinRepository.save(catMagasin);
    }

    /**
     * Get all the catMagasins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CatMagasin> findAll(Pageable pageable) {
        log.debug("Request to get all CatMagasins");
        return catMagasinRepository.findAll(pageable);
    }

    /**
     * Get one catMagasin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CatMagasin> findOne(Long id) {
        log.debug("Request to get CatMagasin : {}", id);
        return catMagasinRepository.findById(id);
    }

    /**
     * Delete the catMagasin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CatMagasin : {}", id);
        catMagasinRepository.deleteById(id);
    }
}
