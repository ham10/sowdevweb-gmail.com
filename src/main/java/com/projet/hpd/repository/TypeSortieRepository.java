package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeSortie;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeSortie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeSortieRepository extends JpaRepository<TypeSortie, Long> {

}
