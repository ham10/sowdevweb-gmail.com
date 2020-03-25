package com.projet.hpd.repository;

import com.projet.hpd.domain.TypePrCharge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePrCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePrChargeRepository extends JpaRepository<TypePrCharge, Long> {

}
