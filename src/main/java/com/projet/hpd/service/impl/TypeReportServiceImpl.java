package com.projet.hpd.service.impl;

import com.projet.hpd.service.TypeReportService;
import com.projet.hpd.domain.TypeReport;
import com.projet.hpd.repository.TypeReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeReport}.
 */
@Service
@Transactional
public class TypeReportServiceImpl implements TypeReportService {

    private final Logger log = LoggerFactory.getLogger(TypeReportServiceImpl.class);

    private final TypeReportRepository typeReportRepository;

    public TypeReportServiceImpl(TypeReportRepository typeReportRepository) {
        this.typeReportRepository = typeReportRepository;
    }

    /**
     * Save a typeReport.
     *
     * @param typeReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeReport save(TypeReport typeReport) {
        log.debug("Request to save TypeReport : {}", typeReport);
        return typeReportRepository.save(typeReport);
    }

    /**
     * Get all the typeReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeReport> findAll(Pageable pageable) {
        log.debug("Request to get all TypeReports");
        return typeReportRepository.findAll(pageable);
    }

    /**
     * Get one typeReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeReport> findOne(Long id) {
        log.debug("Request to get TypeReport : {}", id);
        return typeReportRepository.findById(id);
    }

    /**
     * Delete the typeReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeReport : {}", id);
        typeReportRepository.deleteById(id);
    }
}
