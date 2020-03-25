package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeCaisse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeCaisse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCaisseRepository extends JpaRepository<TypeCaisse, Long> {

}
