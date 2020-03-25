package com.projet.hpd.repository;

import com.projet.hpd.domain.DeptServices;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DeptServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeptServicesRepository extends JpaRepository<DeptServices, Long> {

}
