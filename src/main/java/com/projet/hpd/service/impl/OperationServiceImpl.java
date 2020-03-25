package com.projet.hpd.service.impl;

import com.projet.hpd.service.OperationService;
import com.projet.hpd.domain.Operation;
import com.projet.hpd.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Operation}.
 */
@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationServiceImpl.class);

    private final OperationRepository operationRepository;

    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * Save a operation.
     *
     * @param operation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Operation save(Operation operation) {
        log.debug("Request to save Operation : {}", operation);
        return operationRepository.save(operation);
    }

    /**
     * Get all the operations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Operation> findAll(Pageable pageable) {
        log.debug("Request to get all Operations");
        return operationRepository.findAll(pageable);
    }


    /**
     *  Get all the operations where SchemaComptable is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Operation> findAllWhereSchemaComptableIsNull() {
        log.debug("Request to get all operations where SchemaComptable is null");
        return StreamSupport
            .stream(operationRepository.findAll().spliterator(), false)
            .filter(operation -> operation.getSchemaComptable() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one operation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Operation> findOne(Long id) {
        log.debug("Request to get Operation : {}", id);
        return operationRepository.findById(id);
    }

    /**
     * Delete the operation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.deleteById(id);
    }
}
