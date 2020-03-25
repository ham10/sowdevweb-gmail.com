package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeMvtStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeMvtStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeMvtStockRepository extends JpaRepository<TypeMvtStock, Long> {

}
