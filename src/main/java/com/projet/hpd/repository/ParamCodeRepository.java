package com.projet.hpd.repository;

import com.projet.hpd.domain.ParamCode;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ParamCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamCodeRepository extends JpaRepository<ParamCode, Long> {

}
