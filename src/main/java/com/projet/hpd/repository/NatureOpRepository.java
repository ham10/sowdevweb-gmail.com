package com.projet.hpd.repository;

import com.projet.hpd.domain.NatureOp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NatureOp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureOpRepository extends JpaRepository<NatureOp, Long> {

}
