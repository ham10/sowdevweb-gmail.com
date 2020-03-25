package com.projet.hpd.repository;

import com.projet.hpd.domain.TabAmortis;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TabAmortis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TabAmortisRepository extends JpaRepository<TabAmortis, Long> {

}
