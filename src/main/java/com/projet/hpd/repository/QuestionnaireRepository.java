package com.projet.hpd.repository;

import com.projet.hpd.domain.Questionnaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Questionnaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

}
