package com.projet.hpd.repository;

import com.projet.hpd.domain.ModeRegle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ModeRegle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeRegleRepository extends JpaRepository<ModeRegle, Long> {

}
