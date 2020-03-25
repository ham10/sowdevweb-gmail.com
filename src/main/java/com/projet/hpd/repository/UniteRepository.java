package com.projet.hpd.repository;

import com.projet.hpd.domain.Unite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Unite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniteRepository extends JpaRepository<Unite, Long> {

}
