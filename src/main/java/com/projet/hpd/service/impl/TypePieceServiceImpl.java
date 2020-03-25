package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypePieceService;
import com.projet.hpd.domain.TypePiece;
import com.projet.hpd.repository.TypePieceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypePiece}.
 */
@Service
@Transactional
public class TypePieceServiceImpl implements TypePieceService {

    private final Logger log = LoggerFactory.getLogger(TypePieceServiceImpl.class);

    private final TypePieceRepository typePieceRepository;

    public TypePieceServiceImpl(TypePieceRepository typePieceRepository) {
        this.typePieceRepository = typePieceRepository;
    }

    /**
     * Save a typePiece.
     *
     * @param typePiece the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypePiece save(TypePiece typePiece) {
        log.debug("Request to save TypePiece : {}", typePiece);
        return typePieceRepository.save(typePiece);
    }

    /**
     * Get all the typePieces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePiece> findAll(Pageable pageable) {
        log.debug("Request to get all TypePieces");
        return typePieceRepository.findAll(pageable);
    }

    /**
     * Get one typePiece by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePiece> findOne(Long id) {
        log.debug("Request to get TypePiece : {}", id);
        return typePieceRepository.findById(id);
    }

    /**
     * Delete the typePiece by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePiece : {}", id);
        typePieceRepository.deleteById(id);
    }
}
