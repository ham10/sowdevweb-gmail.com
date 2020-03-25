package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeReport;
import com.projet.hpd.service.TypeReportService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeReport}.
 */
@RestController
@RequestMapping("/api")
public class TypeReportResource {

    private final Logger log = LoggerFactory.getLogger(TypeReportResource.class);

    private static final String ENTITY_NAME = "typeReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeReportService typeReportService;

    public TypeReportResource(TypeReportService typeReportService) {
        this.typeReportService = typeReportService;
    }

    /**
     * {@code POST  /type-reports} : Create a new typeReport.
     *
     * @param typeReport the typeReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeReport, or with status {@code 400 (Bad Request)} if the typeReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-reports")
    public ResponseEntity<TypeReport> createTypeReport(@RequestBody TypeReport typeReport) throws URISyntaxException {
        log.debug("REST request to save TypeReport : {}", typeReport);
        if (typeReport.getId() != null) {
            throw new BadRequestAlertException("A new typeReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeReport result = typeReportService.save(typeReport);
        return ResponseEntity.created(new URI("/api/type-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-reports} : Updates an existing typeReport.
     *
     * @param typeReport the typeReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeReport,
     * or with status {@code 400 (Bad Request)} if the typeReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-reports")
    public ResponseEntity<TypeReport> updateTypeReport(@RequestBody TypeReport typeReport) throws URISyntaxException {
        log.debug("REST request to update TypeReport : {}", typeReport);
        if (typeReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeReport result = typeReportService.save(typeReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-reports} : get all the typeReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeReports in body.
     */
    @GetMapping("/type-reports")
    public ResponseEntity<List<TypeReport>> getAllTypeReports(Pageable pageable) {
        log.debug("REST request to get a page of TypeReports");
        Page<TypeReport> page = typeReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-reports/:id} : get the "id" typeReport.
     *
     * @param id the id of the typeReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-reports/{id}")
    public ResponseEntity<TypeReport> getTypeReport(@PathVariable Long id) {
        log.debug("REST request to get TypeReport : {}", id);
        Optional<TypeReport> typeReport = typeReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeReport);
    }

    /**
     * {@code DELETE  /type-reports/:id} : delete the "id" typeReport.
     *
     * @param id the id of the typeReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-reports/{id}")
    public ResponseEntity<Void> deleteTypeReport(@PathVariable Long id) {
        log.debug("REST request to delete TypeReport : {}", id);
        typeReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
