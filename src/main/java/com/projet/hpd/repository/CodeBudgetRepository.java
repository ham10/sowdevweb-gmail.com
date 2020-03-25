package com.projet.hpd.repository;

import com.projet.hpd.domain.CodeBudget;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CodeBudget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeBudgetRepository extends JpaRepository<CodeBudget, Long> {

}
