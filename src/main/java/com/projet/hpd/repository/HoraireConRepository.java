package com.projet.hpd.repository;

import com.projet.hpd.domain.HoraireCon;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HoraireCon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoraireConRepository extends JpaRepository<HoraireCon, Long> {

}
