package com.projet.hpd.service;

import com.projet.hpd.domain.TypeQuestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypeQuestion}.
 */
public interface TypeQuestionService {

    /**
     * Save a typeQuestion.
     *
     * @param typeQuestion the entity to save.
     * @return the persisted entity.
     */
    TypeQuestion save(TypeQuestion typeQuestion);

    /**
     * Get all the typeQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeQuestion> findAll(Pageable pageable);

    /**
     * Get the "id" typeQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeQuestion> findOne(Long id);

    /**
     * Delete the "id" typeQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
