package com.projet.hpd.repository;

import com.projet.hpd.domain.GroupeSan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GroupeSan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupeSanRepository extends JpaRepository<GroupeSan, Long> {

}
