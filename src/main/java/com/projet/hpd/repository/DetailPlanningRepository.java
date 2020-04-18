package com.projet.hpd.repository;

import com.projet.hpd.domain.DetailPlanning;

import com.projet.hpd.domain.Medecin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the DetailPlanning entity.
 */
@Repository
public interface DetailPlanningRepository extends JpaRepository<DetailPlanning, Long> {
    @Query("Select d from DetailPlanning d , Planning p , Medecin m where d.planning = p AND p.medecin = m AND m.id = ?1 AND d.dateFin >= current_date() ORDER BY d.dateDebut")
    List<DetailPlanning> getAllByMedecin(Long id);


    List<DetailPlanning> getDetailPlanningByDateDebutAndPlanning_Medecin(Instant dateDebut, Medecin planning_medecin);
}
