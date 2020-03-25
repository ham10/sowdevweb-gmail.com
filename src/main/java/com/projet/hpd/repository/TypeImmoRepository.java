package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeImmo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeImmo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeImmoRepository extends JpaRepository<TypeImmo, Long> {

}
