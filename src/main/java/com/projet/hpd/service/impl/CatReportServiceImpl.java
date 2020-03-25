package com.projet.hpd.service.impl;

import com.projet.hpd.service.CatReportService;
import com.projet.hpd.domain.CatReport;
import com.projet.hpd.repository.CatReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CatReport}.
 */
@Service
@Transactional
public class CatReportServiceImpl implements CatReportService {

    private final Logger log = LoggerFactory.getLogger(CatReportServiceImpl.class);

    private final CatReportRepository catReportRepository;

    public CatReportServiceImpl(CatReportRepository catReportRepository) {
        this.catReportRepository = catReportRepository;
    }

    /**
     * Save a catReport.
     *
     * @param catReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CatReport save(CatReport catReport) {
        log.debug("Request to save CatReport : {}", catReport);
        return catReportRepository.save(catReport);
    }

    /**
     * Get all the catReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CatReport> findAll(Pageable pageable) {
        log.debug("Request to get all CatReports");
        return catReportRepository.findAll(pageable);
    }

    /**
     * Get one catReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CatReport> findOne(Long id) {
        log.debug("Request to get CatReport : {}", id);
        return catReportRepository.findById(id);
    }

    /**
     * Delete the catReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CatReport : {}", id);
        catReportRepository.deleteById(id);
    }
}
