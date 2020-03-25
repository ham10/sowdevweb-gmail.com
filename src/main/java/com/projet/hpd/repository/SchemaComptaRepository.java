package com.projet.hpd.repository;

import com.projet.hpd.domain.SchemaCompta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SchemaCompta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchemaComptaRepository extends JpaRepository<SchemaCompta, Long> {

}
