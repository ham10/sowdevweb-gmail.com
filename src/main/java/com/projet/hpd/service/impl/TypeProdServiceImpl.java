package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeProdService;
import com.projet.hpd.domain.TypeProd;
import com.projet.hpd.repository.TypeProdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeProd}.
 */
@Service
@Transactional
public class TypeProdServiceImpl implements TypeProdService {

    private final Logger log = LoggerFactory.getLogger(TypeProdServiceImpl.class);

    private final TypeProdRepository typeProdRepository;

    public TypeProdServiceImpl(TypeProdRepository typeProdRepository) {
        this.typeProdRepository = typeProdRepository;
    }

    /**
     * Save a typeProd.
     *
     * @param typeProd the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeProd save(TypeProd typeProd) {
        log.debug("Request to save TypeProd : {}", typeProd);
        return typeProdRepository.save(typeProd);
    }

    /**
     * Get all the typeProds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeProd> findAll(Pageable pageable) {
        log.debug("Request to get all TypeProds");
        return typeProdRepository.findAll(pageable);
    }

    /**
     * Get one typeProd by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeProd> findOne(Long id) {
        log.debug("Request to get TypeProd : {}", id);
        return typeProdRepository.findById(id);
    }

    /**
     * Delete the typeProd by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeProd : {}", id);
        typeProdRepository.deleteById(id);
    }
}
