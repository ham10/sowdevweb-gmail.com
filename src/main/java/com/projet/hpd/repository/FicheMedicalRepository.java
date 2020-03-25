package com.projet.hpd.repository;

import com.projet.hpd.domain.FicheMedical;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FicheMedical entity.
 */
@Repository
public interface FicheMedicalRepository extends JpaRepository<FicheMedical, Long> {

    @Query(value = "select distinct ficheMedical from FicheMedical ficheMedical left join fetch ficheMedical.typeConstantes",
        countQuery = "select count(distinct ficheMedical) from FicheMedical ficheMedical")
    Page<FicheMedical> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct ficheMedical from FicheMedical ficheMedical left join fetch ficheMedical.typeConstantes")
    List<FicheMedical> findAllWithEagerRelationships();

    @Query("select ficheMedical from FicheMedical ficheMedical left join fetch ficheMedical.typeConstantes where ficheMedical.id =:id")
    Optional<FicheMedical> findOneWithEagerRelationships(@Param("id") Long id);

}
