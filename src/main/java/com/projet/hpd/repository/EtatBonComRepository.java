package com.projet.hpd.repository;

import com.projet.hpd.domain.EtatBonCom;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatBonCom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatBonComRepository extends JpaRepository<EtatBonCom, Long> {

}
