package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeSortieService;
import com.projet.hpd.domain.TypeSortie;
import com.projet.hpd.repository.TypeSortieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeSortie}.
 */
@Service
@Transactional
public class TypeSortieServiceImpl implements TypeSortieService {

    private final Logger log = LoggerFactory.getLogger(TypeSortieServiceImpl.class);

    private final TypeSortieRepository typeSortieRepository;

    public TypeSortieServiceImpl(TypeSortieRepository typeSortieRepository) {
        this.typeSortieRepository = typeSortieRepository;
    }

    /**
     * Save a typeSortie.
     *
     * @param typeSortie the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeSortie save(TypeSortie typeSortie) {
        log.debug("Request to save TypeSortie : {}", typeSortie);
        return typeSortieRepository.save(typeSortie);
    }

    /**
     * Get all the typeSorties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeSortie> findAll(Pageable pageable) {
        log.debug("Request to get all TypeSorties");
        return typeSortieRepository.findAll(pageable);
    }

    /**
     * Get one typeSortie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeSortie> findOne(Long id) {
        log.debug("Request to get TypeSortie : {}", id);
        return typeSortieRepository.findById(id);
    }

    /**
     * Delete the typeSortie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeSortie : {}", id);
        typeSortieRepository.deleteById(id);
    }
}
