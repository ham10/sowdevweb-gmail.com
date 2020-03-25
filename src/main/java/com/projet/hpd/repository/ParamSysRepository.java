package com.projet.hpd.repository;

import com.projet.hpd.domain.ParamSys;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ParamSys entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamSysRepository extends JpaRepository<ParamSys, Long> {

}
