package com.projet.hpd.service.impl;

import com.projet.hpd.service.QuestionnaireService;
import com.projet.hpd.domain.Questionnaire;
import com.projet.hpd.repository.QuestionnaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Questionnaire}.
 */
@Service
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final Logger log = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);

    private final QuestionnaireRepository questionnaireRepository;

    public QuestionnaireServiceImpl(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    /**
     * Save a questionnaire.
     *
     * @param questionnaire the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Questionnaire save(Questionnaire questionnaire) {
        log.debug("Request to save Questionnaire : {}", questionnaire);
        return questionnaireRepository.save(questionnaire);
    }

    /**
     * Get all the questionnaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Questionnaire> findAll(Pageable pageable) {
        log.debug("Request to get all Questionnaires");
        return questionnaireRepository.findAll(pageable);
    }

    /**
     * Get one questionnaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Questionnaire> findOne(Long id) {
        log.debug("Request to get Questionnaire : {}", id);
        return questionnaireRepository.findById(id);
    }

    /**
     * Delete the questionnaire by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Questionnaire : {}", id);
        questionnaireRepository.deleteById(id);
    }
}
