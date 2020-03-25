package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypePatientService;
import com.projet.hpd.domain.TypePatient;
import com.projet.hpd.repository.TypePatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypePatient}.
 */
@Service
@Transactional
public class TypePatientServiceImpl implements TypePatientService {

    private final Logger log = LoggerFactory.getLogger(TypePatientServiceImpl.class);

    private final TypePatientRepository typePatientRepository;

    public TypePatientServiceImpl(TypePatientRepository typePatientRepository) {
        this.typePatientRepository = typePatientRepository;
    }

    /**
     * Save a typePatient.
     *
     * @param typePatient the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypePatient save(TypePatient typePatient) {
        log.debug("Request to save TypePatient : {}", typePatient);
        return typePatientRepository.save(typePatient);
    }

    /**
     * Get all the typePatients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePatient> findAll(Pageable pageable) {
        log.debug("Request to get all TypePatients");
        return typePatientRepository.findAll(pageable);
    }

    /**
     * Get one typePatient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePatient> findOne(Long id) {
        log.debug("Request to get TypePatient : {}", id);
        return typePatientRepository.findById(id);
    }

    /**
     * Delete the typePatient by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePatient : {}", id);
        typePatientRepository.deleteById(id);
    }
}
