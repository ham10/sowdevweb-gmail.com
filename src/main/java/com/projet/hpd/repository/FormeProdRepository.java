package com.projet.hpd.repository;

import com.projet.hpd.domain.FormeProd;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FormeProd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormeProdRepository extends JpaRepository<FormeProd, Long> {

}
