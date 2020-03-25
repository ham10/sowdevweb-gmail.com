package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeCond;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeCond entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCondRepository extends JpaRepository<TypeCond, Long> {

}
