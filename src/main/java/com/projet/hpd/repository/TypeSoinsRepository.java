package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeSoins;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeSoins entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeSoinsRepository extends JpaRepository<TypeSoins, Long> {

}
