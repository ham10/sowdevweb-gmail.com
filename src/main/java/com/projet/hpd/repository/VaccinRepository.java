package com.projet.hpd.repository;

import com.projet.hpd.domain.Vaccin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vaccin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VaccinRepository extends JpaRepository<Vaccin, Long> {

}
