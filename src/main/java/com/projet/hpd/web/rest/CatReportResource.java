package com.projet.hpd.web.rest;

import com.projet.hpd.domain.CatReport;
import com.projet.hpd.service.CatReportService;
import com.projet.hpd.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.projet.hpd.domain.CatReport}.
 */
@RestController
@RequestMapping("/api")
public class CatReportResource {

    private final Logger log = LoggerFactory.getLogger(CatReportResource.class);

    private static final String ENTITY_NAME = "catReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatReportService catReportService;

    public CatReportResource(CatReportService catReportService) {
        this.catReportService = catReportService;
    }

    /**
     * {@code POST  /cat-reports} : Create a new catReport.
     *
     * @param catReport the catReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catReport, or with status {@code 400 (Bad Request)} if the catReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-reports")
    public ResponseEntity<CatReport> createCatReport(@RequestBody CatReport catReport) throws URISyntaxException {
        log.debug("REST request to save CatReport : {}", catReport);
        if (catReport.getId() != null) {
            throw new BadRequestAlertException("A new catReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatReport result = catReportService.save(catReport);
        return ResponseEntity.created(new URI("/api/cat-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-reports} : Updates an existing catReport.
     *
     * @param catReport the catReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catReport,
     * or with status {@code 400 (Bad Request)} if the catReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-reports")
    public ResponseEntity<CatReport> updateCatReport(@RequestBody CatReport catReport) throws URISyntaxException {
        log.debug("REST request to update CatReport : {}", catReport);
        if (catReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatReport result = catReportService.save(catReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-reports} : get all the catReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catReports in body.
     */
    @GetMapping("/cat-reports")
    public ResponseEntity<List<CatReport>> getAllCatReports(Pageable pageable) {
        log.debug("REST request to get a page of CatReports");
        Page<CatReport> page = catReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-reports/:id} : get the "id" catReport.
     *
     * @param id the id of the catReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-reports/{id}")
    public ResponseEntity<CatReport> getCatReport(@PathVariable Long id) {
        log.debug("REST request to get CatReport : {}", id);
        Optional<CatReport> catReport = catReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catReport);
    }

    /**
     * {@code DELETE  /cat-reports/:id} : delete the "id" catReport.
     *
     * @param id the id of the catReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-reports/{id}")
    public ResponseEntity<Void> deleteCatReport(@PathVariable Long id) {
        log.debug("REST request to delete CatReport : {}", id);
        catReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
