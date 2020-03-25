package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeServicesService;
import com.projet.hpd.domain.TypeServices;
import com.projet.hpd.repository.TypeServicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeServices}.
 */
@Service
@Transactional
public class TypeServicesServiceImpl implements TypeServicesService {

    private final Logger log = LoggerFactory.getLogger(TypeServicesServiceImpl.class);

    private final TypeServicesRepository typeServicesRepository;

    public TypeServicesServiceImpl(TypeServicesRepository typeServicesRepository) {
        this.typeServicesRepository = typeServicesRepository;
    }

    /**
     * Save a typeServices.
     *
     * @param typeServices the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeServices save(TypeServices typeServices) {
        log.debug("Request to save TypeServices : {}", typeServices);
        return typeServicesRepository.save(typeServices);
    }

    /**
     * Get all the typeServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeServices> findAll(Pageable pageable) {
        log.debug("Request to get all TypeServices");
        return typeServicesRepository.findAll(pageable);
    }

    /**
     * Get one typeServices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeServices> findOne(Long id) {
        log.debug("Request to get TypeServices : {}", id);
        return typeServicesRepository.findById(id);
    }

    /**
     * Delete the typeServices by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeServices : {}", id);
        typeServicesRepository.deleteById(id);
    }
}
