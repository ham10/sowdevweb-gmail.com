package com.projet.hpd.service.impl;

import com.projet.hpd.domain.Patient;
import com.projet.hpd.service.MedecinService;
import com.projet.hpd.domain.Medecin;
import com.projet.hpd.repository.MedecinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Medecin}.
 */
@Service
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final Logger log = LoggerFactory.getLogger(MedecinServiceImpl.class);

    private final MedecinRepository medecinRepository;

    public MedecinServiceImpl(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    /**
     * Save a medecin.
     *
     * @param medecin the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Medecin save(Medecin medecin) {
        log.debug("Request to save Medecin : {}", medecin);
        return medecinRepository.save(medecin);
    }

    /**
     * Get all the medecins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Medecin> findAll(Pageable pageable) {
        log.debug("Request to get all Medecins");
        return medecinRepository.findAll(pageable);
    }

    /**
     * Get one medecin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Medecin> findOne(Long id) {
        log.debug("Request to get Medecin : {}", id);
        return medecinRepository.findById(id);
    }

    @Override
    public Optional<Medecin> findbynumeropiece(Integer numpiece) {
        log.debug("Request to get Patient : {}", numpiece);
        return medecinRepository.findMedecinByNumeroPiece(numpiece);
    }



    /**
     * Delete the medecin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medecin : {}", id);
        medecinRepository.deleteById(id);
    }
}
