package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeQuestion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeQuestionRepository extends JpaRepository<TypeQuestion, Long> {

}
