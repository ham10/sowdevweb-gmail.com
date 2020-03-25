package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeServices;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeServices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeServicesRepository extends JpaRepository<TypeServices, Long> {

}
