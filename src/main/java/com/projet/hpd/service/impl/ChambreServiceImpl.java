package com.projet.hpd.service.impl;

import com.projet.hpd.service.ChambreService;
import com.projet.hpd.domain.Chambre;
import com.projet.hpd.repository.ChambreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Chambre}.
 */
@Service
@Transactional
public class ChambreServiceImpl implements ChambreService {

    private final Logger log = LoggerFactory.getLogger(ChambreServiceImpl.class);

    private final ChambreRepository chambreRepository;

    public ChambreServiceImpl(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    /**
     * Save a chambre.
     *
     * @param chambre the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Chambre save(Chambre chambre) {
        log.debug("Request to save Chambre : {}", chambre);
        return chambreRepository.save(chambre);
    }

    /**
     * Get all the chambres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Chambre> findAll(Pageable pageable) {
        log.debug("Request to get all Chambres");
        return chambreRepository.findAll(pageable);
    }

    /**
     * Get one chambre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Chambre> findOne(Long id) {
        log.debug("Request to get Chambre : {}", id);
        return chambreRepository.findById(id);
    }

    /**
     * Delete the chambre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chambre : {}", id);
        chambreRepository.deleteById(id);
    }
}
