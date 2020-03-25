package com.projet.hpd.repository;

import com.projet.hpd.domain.RDV;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RDV entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RDVRepository extends JpaRepository<RDV, Long> {

}
