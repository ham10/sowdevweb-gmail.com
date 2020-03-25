package com.projet.hpd.service;

import com.projet.hpd.domain.CodeBudget;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CodeBudget}.
 */
public interface CodeBudgetService {

    /**
     * Save a codeBudget.
     *
     * @param codeBudget the entity to save.
     * @return the persisted entity.
     */
    CodeBudget save(CodeBudget codeBudget);

    /**
     * Get all the codeBudgets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CodeBudget> findAll(Pageable pageable);

    /**
     * Get the "id" codeBudget.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CodeBudget> findOne(Long id);

    /**
     * Delete the "id" codeBudget.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
