package com.projet.hpd.repository;

import com.projet.hpd.domain.Etagere;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Etagere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtagereRepository extends JpaRepository<Etagere, Long> {

}
