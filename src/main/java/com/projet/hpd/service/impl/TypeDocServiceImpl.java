package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeDocService;
import com.projet.hpd.domain.TypeDoc;
import com.projet.hpd.repository.TypeDocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeDoc}.
 */
@Service
@Transactional
public class TypeDocServiceImpl implements TypeDocService {

    private final Logger log = LoggerFactory.getLogger(TypeDocServiceImpl.class);

    private final TypeDocRepository typeDocRepository;

    public TypeDocServiceImpl(TypeDocRepository typeDocRepository) {
        this.typeDocRepository = typeDocRepository;
    }

    /**
     * Save a typeDoc.
     *
     * @param typeDoc the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeDoc save(TypeDoc typeDoc) {
        log.debug("Request to save TypeDoc : {}", typeDoc);
        return typeDocRepository.save(typeDoc);
    }

    /**
     * Get all the typeDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeDoc> findAll(Pageable pageable) {
        log.debug("Request to get all TypeDocs");
        return typeDocRepository.findAll(pageable);
    }

    /**
     * Get one typeDoc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeDoc> findOne(Long id) {
        log.debug("Request to get TypeDoc : {}", id);
        return typeDocRepository.findById(id);
    }

    /**
     * Delete the typeDoc by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeDoc : {}", id);
        typeDocRepository.deleteById(id);
    }
}
