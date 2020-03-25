package com.projet.hpd.repository;

import com.projet.hpd.domain.ParamDivers;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ParamDivers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamDiversRepository extends JpaRepository<ParamDivers, Long> {

}
