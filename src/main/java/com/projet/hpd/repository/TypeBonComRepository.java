package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeBonCom;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeBonCom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeBonComRepository extends JpaRepository<TypeBonCom, Long> {

}
