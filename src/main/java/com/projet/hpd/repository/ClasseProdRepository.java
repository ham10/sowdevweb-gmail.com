package com.projet.hpd.repository;

import com.projet.hpd.domain.ClasseProd;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClasseProd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseProdRepository extends JpaRepository<ClasseProd, Long> {

}
