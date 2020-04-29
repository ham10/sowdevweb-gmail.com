package com.projet.hpd.repository;

import com.projet.hpd.domain.Medecin;

import com.projet.hpd.domain.Patient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Medecin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    public Optional<Medecin> findMedecinByNumeroPiece(Integer numpiece);

}
