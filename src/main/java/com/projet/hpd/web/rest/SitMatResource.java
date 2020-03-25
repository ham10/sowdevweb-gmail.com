package com.projet.hpd.web.rest;

import com.projet.hpd.domain.SitMat;
import com.projet.hpd.service.SitMatService;
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
 * REST controller for managing {@link com.projet.hpd.domain.SitMat}.
 */
@RestController
@RequestMapping("/api")
public class SitMatResource {

    private final Logger log = LoggerFactory.getLogger(SitMatResource.class);

    private static final String ENTITY_NAME = "sitMat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SitMatService sitMatService;

    public SitMatResource(SitMatService sitMatService) {
        this.sitMatService = sitMatService;
    }

    /**
     * {@code POST  /sit-mats} : Create a new sitMat.
     *
     * @param sitMat the sitMat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sitMat, or with status {@code 400 (Bad Request)} if the sitMat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sit-mats")
    public ResponseEntity<SitMat> createSitMat(@RequestBody SitMat sitMat) throws URISyntaxException {
        log.debug("REST request to save SitMat : {}", sitMat);
        if (sitMat.getId() != null) {
            throw new BadRequestAlertException("A new sitMat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SitMat result = sitMatService.save(sitMat);
        return ResponseEntity.created(new URI("/api/sit-mats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sit-mats} : Updates an existing sitMat.
     *
     * @param sitMat the sitMat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sitMat,
     * or with status {@code 400 (Bad Request)} if the sitMat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sitMat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sit-mats")
    public ResponseEntity<SitMat> updateSitMat(@RequestBody SitMat sitMat) throws URISyntaxException {
        log.debug("REST request to update SitMat : {}", sitMat);
        if (sitMat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SitMat result = sitMatService.save(sitMat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sitMat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sit-mats} : get all the sitMats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sitMats in body.
     */
    @GetMapping("/sit-mats")
    public ResponseEntity<List<SitMat>> getAllSitMats(Pageable pageable) {
        log.debug("REST request to get a page of SitMats");
        Page<SitMat> page = sitMatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sit-mats/:id} : get the "id" sitMat.
     *
     * @param id the id of the sitMat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sitMat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sit-mats/{id}")
    public ResponseEntity<SitMat> getSitMat(@PathVariable Long id) {
        log.debug("REST request to get SitMat : {}", id);
        Optional<SitMat> sitMat = sitMatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sitMat);
    }

    /**
     * {@code DELETE  /sit-mats/:id} : delete the "id" sitMat.
     *
     * @param id the id of the sitMat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sit-mats/{id}")
    public ResponseEntity<Void> deleteSitMat(@PathVariable Long id) {
        log.debug("REST request to delete SitMat : {}", id);
        sitMatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
