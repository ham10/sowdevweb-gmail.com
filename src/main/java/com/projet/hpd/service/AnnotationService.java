package com.projet.hpd.service;

import com.projet.hpd.domain.Annotation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Annotation}.
 */
public interface AnnotationService {

    /**
     * Save a annotation.
     *
     * @param annotation the entity to save.
     * @return the persisted entity.
     */
    Annotation save(Annotation annotation);

    /**
     * Get all the annotations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Annotation> findAll(Pageable pageable);

    /**
     * Get the "id" annotation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Annotation> findOne(Long id);

    /**
     * Delete the "id" annotation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
