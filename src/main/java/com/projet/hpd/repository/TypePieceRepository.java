package com.projet.hpd.repository;

import com.projet.hpd.domain.TypePiece;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypePiece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePieceRepository extends JpaRepository<TypePiece, Long> {

}
