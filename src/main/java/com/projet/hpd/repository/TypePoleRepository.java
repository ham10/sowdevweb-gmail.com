package com.projet.hpd.repository;

import com.projet.hpd.domain.TypePole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePoleRepository extends JpaRepository<TypePole, Long> {

}
