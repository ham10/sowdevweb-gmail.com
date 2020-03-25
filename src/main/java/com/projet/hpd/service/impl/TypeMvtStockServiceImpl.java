package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeMvtStockService;
import com.projet.hpd.domain.TypeMvtStock;
import com.projet.hpd.repository.TypeMvtStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeMvtStock}.
 */
@Service
@Transactional
public class TypeMvtStockServiceImpl implements TypeMvtStockService {

    private final Logger log = LoggerFactory.getLogger(TypeMvtStockServiceImpl.class);

    private final TypeMvtStockRepository typeMvtStockRepository;

    public TypeMvtStockServiceImpl(TypeMvtStockRepository typeMvtStockRepository) {
        this.typeMvtStockRepository = typeMvtStockRepository;
    }

    /**
     * Save a typeMvtStock.
     *
     * @param typeMvtStock the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeMvtStock save(TypeMvtStock typeMvtStock) {
        log.debug("Request to save TypeMvtStock : {}", typeMvtStock);
        return typeMvtStockRepository.save(typeMvtStock);
    }

    /**
     * Get all the typeMvtStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeMvtStock> findAll(Pageable pageable) {
        log.debug("Request to get all TypeMvtStocks");
        return typeMvtStockRepository.findAll(pageable);
    }

    /**
     * Get one typeMvtStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeMvtStock> findOne(Long id) {
        log.debug("Request to get TypeMvtStock : {}", id);
        return typeMvtStockRepository.findById(id);
    }

    /**
     * Delete the typeMvtStock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeMvtStock : {}", id);
        typeMvtStockRepository.deleteById(id);
    }
}
