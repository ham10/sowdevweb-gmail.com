package com.projet.hpd.service.impl;

import com.projet.hpd.service.ServicesService;
import com.projet.hpd.domain.Services;
import com.projet.hpd.repository.ServicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Services}.
 */
@Service
@Transactional
public class ServicesServiceImpl implements ServicesService {

    private final Logger log = LoggerFactory.getLogger(ServicesServiceImpl.class);

    private final ServicesRepository servicesRepository;

    public ServicesServiceImpl(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    /**
     * Save a services.
     *
     * @param services the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Services save(Services services) {
        log.debug("Request to save Services : {}", services);
        return servicesRepository.save(services);
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Services> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return servicesRepository.findAll(pageable);
    }

    /**
     * Get one services by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Services> findOne(Long id) {
        log.debug("Request to get Services : {}", id);
        return servicesRepository.findById(id);
    }

    /**
     * Delete the services by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Services : {}", id);
        servicesRepository.deleteById(id);
    }
}
