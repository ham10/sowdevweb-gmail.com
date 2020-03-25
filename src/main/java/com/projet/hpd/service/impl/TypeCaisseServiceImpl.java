package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeCaisseService;
import com.projet.hpd.domain.TypeCaisse;
import com.projet.hpd.repository.TypeCaisseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeCaisse}.
 */
@Service
@Transactional
public class TypeCaisseServiceImpl implements TypeCaisseService {

    private final Logger log = LoggerFactory.getLogger(TypeCaisseServiceImpl.class);

    private final TypeCaisseRepository typeCaisseRepository;

    public TypeCaisseServiceImpl(TypeCaisseRepository typeCaisseRepository) {
        this.typeCaisseRepository = typeCaisseRepository;
    }

    /**
     * Save a typeCaisse.
     *
     * @param typeCaisse the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeCaisse save(TypeCaisse typeCaisse) {
        log.debug("Request to save TypeCaisse : {}", typeCaisse);
        return typeCaisseRepository.save(typeCaisse);
    }

    /**
     * Get all the typeCaisses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeCaisse> findAll(Pageable pageable) {
        log.debug("Request to get all TypeCaisses");
        return typeCaisseRepository.findAll(pageable);
    }

    /**
     * Get one typeCaisse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeCaisse> findOne(Long id) {
        log.debug("Request to get TypeCaisse : {}", id);
        return typeCaisseRepository.findById(id);
    }

    /**
     * Delete the typeCaisse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeCaisse : {}", id);
        typeCaisseRepository.deleteById(id);
    }
}
