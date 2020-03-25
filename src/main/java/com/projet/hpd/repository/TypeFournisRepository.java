package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeFournis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeFournis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeFournisRepository extends JpaRepository<TypeFournis, Long> {

}
