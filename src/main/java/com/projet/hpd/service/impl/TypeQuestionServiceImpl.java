package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeQuestionService;
import com.projet.hpd.domain.TypeQuestion;
import com.projet.hpd.repository.TypeQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeQuestion}.
 */
@Service
@Transactional
public class TypeQuestionServiceImpl implements TypeQuestionService {

    private final Logger log = LoggerFactory.getLogger(TypeQuestionServiceImpl.class);

    private final TypeQuestionRepository typeQuestionRepository;

    public TypeQuestionServiceImpl(TypeQuestionRepository typeQuestionRepository) {
        this.typeQuestionRepository = typeQuestionRepository;
    }

    /**
     * Save a typeQuestion.
     *
     * @param typeQuestion the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeQuestion save(TypeQuestion typeQuestion) {
        log.debug("Request to save TypeQuestion : {}", typeQuestion);
        return typeQuestionRepository.save(typeQuestion);
    }

    /**
     * Get all the typeQuestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeQuestion> findAll(Pageable pageable) {
        log.debug("Request to get all TypeQuestions");
        return typeQuestionRepository.findAll(pageable);
    }

    /**
     * Get one typeQuestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeQuestion> findOne(Long id) {
        log.debug("Request to get TypeQuestion : {}", id);
        return typeQuestionRepository.findById(id);
    }

    /**
     * Delete the typeQuestion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeQuestion : {}", id);
        typeQuestionRepository.deleteById(id);
    }
}
