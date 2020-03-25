package com.projet.hpd.service;

import com.projet.hpd.domain.Questionnaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Questionnaire}.
 */
public interface QuestionnaireService {

    /**
     * Save a questionnaire.
     *
     * @param questionnaire the entity to save.
     * @return the persisted entity.
     */
    Questionnaire save(Questionnaire questionnaire);

    /**
     * Get all the questionnaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Questionnaire> findAll(Pageable pageable);

    /**
     * Get the "id" questionnaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Questionnaire> findOne(Long id);

    /**
     * Delete the "id" questionnaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
