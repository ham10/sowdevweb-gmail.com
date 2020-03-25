package com.projet.hpd.service;

import com.projet.hpd.domain.ClasseProd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ClasseProd}.
 */
public interface ClasseProdService {

    /**
     * Save a classeProd.
     *
     * @param classeProd the entity to save.
     * @return the persisted entity.
     */
    ClasseProd save(ClasseProd classeProd);

    /**
     * Get all the classeProds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClasseProd> findAll(Pageable pageable);

    /**
     * Get the "id" classeProd.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClasseProd> findOne(Long id);

    /**
     * Delete the "id" classeProd.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
