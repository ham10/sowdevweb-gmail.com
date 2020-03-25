package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeAntecedent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeAntecedent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAntecedentRepository extends JpaRepository<TypeAntecedent, Long> {

}
