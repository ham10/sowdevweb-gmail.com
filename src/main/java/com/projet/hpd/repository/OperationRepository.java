package com.projet.hpd.repository;

import com.projet.hpd.domain.Operation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Operation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

}
