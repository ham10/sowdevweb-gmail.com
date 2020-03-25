package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypePrChargeService;
import com.projet.hpd.domain.TypePrCharge;
import com.projet.hpd.repository.TypePrChargeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypePrCharge}.
 */
@Service
@Transactional
public class TypePrChargeServiceImpl implements TypePrChargeService {

    private final Logger log = LoggerFactory.getLogger(TypePrChargeServiceImpl.class);

    private final TypePrChargeRepository typePrChargeRepository;

    public TypePrChargeServiceImpl(TypePrChargeRepository typePrChargeRepository) {
        this.typePrChargeRepository = typePrChargeRepository;
    }

    /**
     * Save a typePrCharge.
     *
     * @param typePrCharge the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypePrCharge save(TypePrCharge typePrCharge) {
        log.debug("Request to save TypePrCharge : {}", typePrCharge);
        return typePrChargeRepository.save(typePrCharge);
    }

    /**
     * Get all the typePrCharges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePrCharge> findAll(Pageable pageable) {
        log.debug("Request to get all TypePrCharges");
        return typePrChargeRepository.findAll(pageable);
    }

    /**
     * Get one typePrCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePrCharge> findOne(Long id) {
        log.debug("Request to get TypePrCharge : {}", id);
        return typePrChargeRepository.findById(id);
    }

    /**
     * Delete the typePrCharge by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePrCharge : {}", id);
        typePrChargeRepository.deleteById(id);
    }
}
