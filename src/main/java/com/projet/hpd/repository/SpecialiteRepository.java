package com.projet.hpd.repository;

import com.projet.hpd.domain.Specialite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Specialite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {

}
