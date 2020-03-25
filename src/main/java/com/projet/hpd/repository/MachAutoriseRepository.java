package com.projet.hpd.repository;

import com.projet.hpd.domain.MachAutorise;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MachAutorise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MachAutoriseRepository extends JpaRepository<MachAutorise, Long> {

}
