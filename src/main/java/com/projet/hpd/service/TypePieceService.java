package com.projet.hpd.service;

import com.projet.hpd.domain.TypePiece;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TypePiece}.
 */
public interface TypePieceService {

    /**
     * Save a typePiece.
     *
     * @param typePiece the entity to save.
     * @return the persisted entity.
     */
    TypePiece save(TypePiece typePiece);

    /**
     * Get all the typePieces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypePiece> findAll(Pageable pageable);

    /**
     * Get the "id" typePiece.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypePiece> findOne(Long id);

    /**
     * Delete the "id" typePiece.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
