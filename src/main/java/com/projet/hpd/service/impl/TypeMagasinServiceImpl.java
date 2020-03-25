package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeMagasinService;
import com.projet.hpd.domain.TypeMagasin;
import com.projet.hpd.repository.TypeMagasinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeMagasin}.
 */
@Service
@Transactional
public class TypeMagasinServiceImpl implements TypeMagasinService {

    private final Logger log = LoggerFactory.getLogger(TypeMagasinServiceImpl.class);

    private final TypeMagasinRepository typeMagasinRepository;

    public TypeMagasinServiceImpl(TypeMagasinRepository typeMagasinRepository) {
        this.typeMagasinRepository = typeMagasinRepository;
    }

    /**
     * Save a typeMagasin.
     *
     * @param typeMagasin the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeMagasin save(TypeMagasin typeMagasin) {
        log.debug("Request to save TypeMagasin : {}", typeMagasin);
        return typeMagasinRepository.save(typeMagasin);
    }

    /**
     * Get all the typeMagasins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeMagasin> findAll(Pageable pageable) {
        log.debug("Request to get all TypeMagasins");
        return typeMagasinRepository.findAll(pageable);
    }

    /**
     * Get one typeMagasin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeMagasin> findOne(Long id) {
        log.debug("Request to get TypeMagasin : {}", id);
        return typeMagasinRepository.findById(id);
    }

    /**
     * Delete the typeMagasin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeMagasin : {}", id);
        typeMagasinRepository.deleteById(id);
    }
}
