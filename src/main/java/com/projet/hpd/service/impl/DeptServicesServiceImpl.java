package com.projet.hpd.service.impl;

import com.projet.hpd.service.DeptServicesService;
import com.projet.hpd.domain.DeptServices;
import com.projet.hpd.repository.DeptServicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeptServices}.
 */
@Service
@Transactional
public class DeptServicesServiceImpl implements DeptServicesService {

    private final Logger log = LoggerFactory.getLogger(DeptServicesServiceImpl.class);

    private final DeptServicesRepository deptServicesRepository;

    public DeptServicesServiceImpl(DeptServicesRepository deptServicesRepository) {
        this.deptServicesRepository = deptServicesRepository;
    }

    /**
     * Save a deptServices.
     *
     * @param deptServices the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeptServices save(DeptServices deptServices) {
        log.debug("Request to save DeptServices : {}", deptServices);
        return deptServicesRepository.save(deptServices);
    }

    /**
     * Get all the deptServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeptServices> findAll(Pageable pageable) {
        log.debug("Request to get all DeptServices");
        return deptServicesRepository.findAll(pageable);
    }

    /**
     * Get one deptServices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeptServices> findOne(Long id) {
        log.debug("Request to get DeptServices : {}", id);
        return deptServicesRepository.findById(id);
    }

    /**
     * Delete the deptServices by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeptServices : {}", id);
        deptServicesRepository.deleteById(id);
    }
}
