package com.projet.hpd.repository;

import com.projet.hpd.domain.EtatOperation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatOperationRepository extends JpaRepository<EtatOperation, Long> {

}
