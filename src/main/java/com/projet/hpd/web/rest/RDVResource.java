package com.projet.hpd.web.rest;

import com.projet.hpd.domain.RDV;
import com.projet.hpd.service.RDVService;
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
 * REST controller for managing {@link com.projet.hpd.domain.RDV}.
 */
@RestController
@RequestMapping("/api")
public class RDVResource {

    private final Logger log = LoggerFactory.getLogger(RDVResource.class);

    private static final String ENTITY_NAME = "rDV";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RDVService rDVService;

    public RDVResource(RDVService rDVService) {
        this.rDVService = rDVService;
    }

    /**
     * {@code POST  /rdvs} : Create a new rDV.
     *
     * @param rDV the rDV to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rDV, or with status {@code 400 (Bad Request)} if the rDV has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rdvs")
    public ResponseEntity<RDV> createRDV(@RequestBody RDV rDV) throws URISyntaxException {
        log.debug("REST request to save RDV : {}", rDV);
        if (rDV.getId() != null) {
            throw new BadRequestAlertException("A new rDV cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RDV result = rDVService.save(rDV);
        return ResponseEntity.created(new URI("/api/rdvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rdvs} : Updates an existing rDV.
     *
     * @param rDV the rDV to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rDV,
     * or with status {@code 400 (Bad Request)} if the rDV is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rDV couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rdvs")
    public ResponseEntity<RDV> updateRDV(@RequestBody RDV rDV) throws URISyntaxException {
        log.debug("REST request to update RDV : {}", rDV);
        if (rDV.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RDV result = rDVService.save(rDV);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rDV.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rdvs} : get all the rDVS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rDVS in body.
     */
    @GetMapping("/rdvs")
    public ResponseEntity<List<RDV>> getAllRDVS(Pageable pageable) {
        log.debug("REST request to get a page of RDVS");
        Page<RDV> page = rDVService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rdvs/:id} : get the "id" rDV.
     *
     * @param id the id of the rDV to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rDV, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rdvs/{id}")
    public ResponseEntity<RDV> getRDV(@PathVariable Long id) {
        log.debug("REST request to get RDV : {}", id);
        Optional<RDV> rDV = rDVService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rDV);
    }

    /**
     * {@code DELETE  /rdvs/:id} : delete the "id" rDV.
     *
     * @param id the id of the rDV to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rdvs/{id}")
    public ResponseEntity<Void> deleteRDV(@PathVariable Long id) {
        log.debug("REST request to delete RDV : {}", id);
        rDVService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
