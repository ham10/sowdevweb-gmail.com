package com.projet.hpd.repository;

import com.projet.hpd.domain.Etablis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Etablis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtablisRepository extends JpaRepository<Etablis, Long> {

}
