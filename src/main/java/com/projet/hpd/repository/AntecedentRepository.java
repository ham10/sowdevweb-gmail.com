package com.projet.hpd.repository;

import com.projet.hpd.domain.Antecedent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Antecedent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntecedentRepository extends JpaRepository<Antecedent, Long> {

}
