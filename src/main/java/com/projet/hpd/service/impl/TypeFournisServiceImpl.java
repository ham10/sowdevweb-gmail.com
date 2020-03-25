package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeFournisService;
import com.projet.hpd.domain.TypeFournis;
import com.projet.hpd.repository.TypeFournisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeFournis}.
 */
@Service
@Transactional
public class TypeFournisServiceImpl implements TypeFournisService {

    private final Logger log = LoggerFactory.getLogger(TypeFournisServiceImpl.class);

    private final TypeFournisRepository typeFournisRepository;

    public TypeFournisServiceImpl(TypeFournisRepository typeFournisRepository) {
        this.typeFournisRepository = typeFournisRepository;
    }

    /**
     * Save a typeFournis.
     *
     * @param typeFournis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeFournis save(TypeFournis typeFournis) {
        log.debug("Request to save TypeFournis : {}", typeFournis);
        return typeFournisRepository.save(typeFournis);
    }

    /**
     * Get all the typeFournis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeFournis> findAll(Pageable pageable) {
        log.debug("Request to get all TypeFournis");
        return typeFournisRepository.findAll(pageable);
    }

    /**
     * Get one typeFournis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeFournis> findOne(Long id) {
        log.debug("Request to get TypeFournis : {}", id);
        return typeFournisRepository.findById(id);
    }

    /**
     * Delete the typeFournis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeFournis : {}", id);
        typeFournisRepository.deleteById(id);
    }
}
