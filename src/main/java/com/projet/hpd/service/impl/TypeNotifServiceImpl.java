package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeNotifService;
import com.projet.hpd.domain.TypeNotif;
import com.projet.hpd.repository.TypeNotifRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeNotif}.
 */
@Service
@Transactional
public class TypeNotifServiceImpl implements TypeNotifService {

    private final Logger log = LoggerFactory.getLogger(TypeNotifServiceImpl.class);

    private final TypeNotifRepository typeNotifRepository;

    public TypeNotifServiceImpl(TypeNotifRepository typeNotifRepository) {
        this.typeNotifRepository = typeNotifRepository;
    }

    /**
     * Save a typeNotif.
     *
     * @param typeNotif the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeNotif save(TypeNotif typeNotif) {
        log.debug("Request to save TypeNotif : {}", typeNotif);
        return typeNotifRepository.save(typeNotif);
    }

    /**
     * Get all the typeNotifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeNotif> findAll(Pageable pageable) {
        log.debug("Request to get all TypeNotifs");
        return typeNotifRepository.findAll(pageable);
    }

    /**
     * Get one typeNotif by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeNotif> findOne(Long id) {
        log.debug("Request to get TypeNotif : {}", id);
        return typeNotifRepository.findById(id);
    }

    /**
     * Delete the typeNotif by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeNotif : {}", id);
        typeNotifRepository.deleteById(id);
    }
}
