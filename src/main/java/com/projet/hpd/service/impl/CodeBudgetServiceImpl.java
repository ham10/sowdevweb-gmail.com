package com.projet.hpd.service.impl;

import com.projet.hpd.service.CodeBudgetService;
import com.projet.hpd.domain.CodeBudget;
import com.projet.hpd.repository.CodeBudgetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CodeBudget}.
 */
@Service
@Transactional
public class CodeBudgetServiceImpl implements CodeBudgetService {

    private final Logger log = LoggerFactory.getLogger(CodeBudgetServiceImpl.class);

    private final CodeBudgetRepository codeBudgetRepository;

    public CodeBudgetServiceImpl(CodeBudgetRepository codeBudgetRepository) {
        this.codeBudgetRepository = codeBudgetRepository;
    }

    /**
     * Save a codeBudget.
     *
     * @param codeBudget the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CodeBudget save(CodeBudget codeBudget) {
        log.debug("Request to save CodeBudget : {}", codeBudget);
        return codeBudgetRepository.save(codeBudget);
    }

    /**
     * Get all the codeBudgets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CodeBudget> findAll(Pageable pageable) {
        log.debug("Request to get all CodeBudgets");
        return codeBudgetRepository.findAll(pageable);
    }

    /**
     * Get one codeBudget by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CodeBudget> findOne(Long id) {
        log.debug("Request to get CodeBudget : {}", id);
        return codeBudgetRepository.findById(id);
    }

    /**
     * Delete the codeBudget by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CodeBudget : {}", id);
        codeBudgetRepository.deleteById(id);
    }
}
