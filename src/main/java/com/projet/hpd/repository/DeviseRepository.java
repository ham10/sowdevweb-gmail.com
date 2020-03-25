package com.projet.hpd.repository;

import com.projet.hpd.domain.Devise;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Devise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviseRepository extends JpaRepository<Devise, Long> {

}
