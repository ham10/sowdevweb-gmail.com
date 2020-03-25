package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeMagasin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeMagasin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeMagasinRepository extends JpaRepository<TypeMagasin, Long> {

}
