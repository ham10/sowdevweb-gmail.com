package com.projet.hpd.repository;

import com.projet.hpd.domain.ResultatActe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ResultatActe entity.
 */
@Repository
public interface ResultatActeRepository extends JpaRepository<ResultatActe, Long> {

    @Query(value = "select distinct resultatActe from ResultatActe resultatActe left join fetch resultatActe.acteMedicals",
        countQuery = "select count(distinct resultatActe) from ResultatActe resultatActe")
    Page<ResultatActe> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct resultatActe from ResultatActe resultatActe left join fetch resultatActe.acteMedicals")
    List<ResultatActe> findAllWithEagerRelationships();

    @Query("select resultatActe from ResultatActe resultatActe left join fetch resultatActe.acteMedicals where resultatActe.id =:id")
    Optional<ResultatActe> findOneWithEagerRelationships(@Param("id") Long id);

}
