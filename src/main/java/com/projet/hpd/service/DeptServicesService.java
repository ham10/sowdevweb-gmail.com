package com.projet.hpd.service;

import com.projet.hpd.domain.DeptServices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DeptServices}.
 */
public interface DeptServicesService {

    /**
     * Save a deptServices.
     *
     * @param deptServices the entity to save.
     * @return the persisted entity.
     */
    DeptServices save(DeptServices deptServices);

    /**
     * Get all the deptServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeptServices> findAll(Pageable pageable);

    /**
     * Get the "id" deptServices.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeptServices> findOne(Long id);

    /**
     * Delete the "id" deptServices.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
