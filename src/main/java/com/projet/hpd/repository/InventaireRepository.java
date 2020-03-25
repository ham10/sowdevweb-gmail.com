package com.projet.hpd.repository;

import com.projet.hpd.domain.Inventaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inventaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventaireRepository extends JpaRepository<Inventaire, Long> {

}
