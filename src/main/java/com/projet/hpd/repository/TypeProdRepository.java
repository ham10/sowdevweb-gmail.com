package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeProd;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeProd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeProdRepository extends JpaRepository<TypeProd, Long> {

}
