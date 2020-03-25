package com.projet.hpd.service.impl;

import com.projet.hpd.service.CatChambreService;
import com.projet.hpd.domain.CatChambre;
import com.projet.hpd.repository.CatChambreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatChambre}.
 */
@Service
@Transactional
public class CatChambreServiceImpl implements CatChambreService {

    private final Logger log = LoggerFactory.getLogger(CatChambreServiceImpl.class);

    private final CatChambreRepository catChambreRepository;

    public CatChambreServiceImpl(CatChambreRepository catChambreRepository) {
        this.catChambreRepository = catChambreRepository;
    }

    /**
     * Save a catChambre.
     *
     * @param catChambre the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CatChambre save(CatChambre catChambre) {
        log.debug("Request to save CatChambre : {}", catChambre);
        return catChambreRepository.save(catChambre);
    }

    /**
     * Get all the catChambres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CatChambre> findAll(Pageable pageable) {
        log.debug("Request to get all CatChambres");
        return catChambreRepository.findAll(pageable);
    }

    /**
     * Get one catChambre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CatChambre> findOne(Long id) {
        log.debug("Request to get CatChambre : {}", id);
        return catChambreRepository.findById(id);
    }

    /**
     * Delete the catChambre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CatChambre : {}", id);
        catChambreRepository.deleteById(id);
    }
}
