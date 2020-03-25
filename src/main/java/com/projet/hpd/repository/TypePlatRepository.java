package com.projet.hpd.repository;

import com.projet.hpd.domain.TypePlat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePlat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePlatRepository extends JpaRepository<TypePlat, Long> {

}
