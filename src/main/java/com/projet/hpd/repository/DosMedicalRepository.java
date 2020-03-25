package com.projet.hpd.repository;

import com.projet.hpd.domain.DosMedical;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DosMedical entity.
 */
@Repository
public interface DosMedicalRepository extends JpaRepository<DosMedical, Long> {

    @Query(value = "select distinct dosMedical from DosMedical dosMedical left join fetch dosMedical.antecedents",
        countQuery = "select count(distinct dosMedical) from DosMedical dosMedical")
    Page<DosMedical> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dosMedical from DosMedical dosMedical left join fetch dosMedical.antecedents")
    List<DosMedical> findAllWithEagerRelationships();

    @Query("select dosMedical from DosMedical dosMedical left join fetch dosMedical.antecedents where dosMedical.id =:id")
    Optional<DosMedical> findOneWithEagerRelationships(@Param("id") Long id);

}
