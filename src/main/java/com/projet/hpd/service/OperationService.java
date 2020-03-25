package com.projet.hpd.service;

import com.projet.hpd.domain.Operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Operation}.
 */
public interface OperationService {

    /**
     * Save a operation.
     *
     * @param operation the entity to save.
     * @return the persisted entity.
     */
    Operation save(Operation operation);

    /**
     * Get all the operations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Operation> findAll(Pageable pageable);
    /**
     * Get all the OperationDTO where SchemaComptable is {@code null}.
     *
     * @return the list of entities.
     */
    List<Operation> findAllWhereSchemaComptableIsNull();

    /**
     * Get the "id" operation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Operation> findOne(Long id);

    /**
     * Delete the "id" operation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
