package com.projet.hpd.repository;

import com.projet.hpd.domain.TypeNotif;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeNotif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeNotifRepository extends JpaRepository<TypeNotif, Long> {

}
