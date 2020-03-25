package com.projet.hpd.service.impl;

import com.projet.hpd.service.HoraireConService;
import com.projet.hpd.domain.HoraireCon;
import com.projet.hpd.repository.HoraireConRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link HoraireCon}.
 */
@Service
@Transactional
public class HoraireConServiceImpl implements HoraireConService {

    private final Logger log = LoggerFactory.getLogger(HoraireConServiceImpl.class);

    private final HoraireConRepository horaireConRepository;

    public HoraireConServiceImpl(HoraireConRepository horaireConRepository) {
        this.horaireConRepository = horaireConRepository;
    }

    /**
     * Save a horaireCon.
     *
     * @param horaireCon the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HoraireCon save(HoraireCon horaireCon) {
        log.debug("Request to save HoraireCon : {}", horaireCon);
        return horaireConRepository.save(horaireCon);
    }

    /**
     * Get all the horaireCons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HoraireCon> findAll(Pageable pageable) {
        log.debug("Request to get all HoraireCons");
        return horaireConRepository.findAll(pageable);
    }

    /**
     * Get one horaireCon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HoraireCon> findOne(Long id) {
        log.debug("Request to get HoraireCon : {}", id);
        return horaireConRepository.findById(id);
    }

    /**
     * Delete the horaireCon by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HoraireCon : {}", id);
        horaireConRepository.deleteById(id);
    }
}
