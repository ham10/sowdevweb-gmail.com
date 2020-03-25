package com.projet.hpd.web.rest;

import com.projet.hpd.domain.NatureOp;
import com.projet.hpd.service.NatureOpService;
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
 * REST controller for managing {@link com.projet.hpd.domain.NatureOp}.
 */
@RestController
@RequestMapping("/api")
public class NatureOpResource {

    private final Logger log = LoggerFactory.getLogger(NatureOpResource.class);

    private static final String ENTITY_NAME = "natureOp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureOpService natureOpService;

    public NatureOpResource(NatureOpService natureOpService) {
        this.natureOpService = natureOpService;
    }

    /**
     * {@code POST  /nature-ops} : Create a new natureOp.
     *
     * @param natureOp the natureOp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureOp, or with status {@code 400 (Bad Request)} if the natureOp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-ops")
    public ResponseEntity<NatureOp> createNatureOp(@RequestBody NatureOp natureOp) throws URISyntaxException {
        log.debug("REST request to save NatureOp : {}", natureOp);
        if (natureOp.getId() != null) {
            throw new BadRequestAlertException("A new natureOp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureOp result = natureOpService.save(natureOp);
        return ResponseEntity.created(new URI("/api/nature-ops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-ops} : Updates an existing natureOp.
     *
     * @param natureOp the natureOp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureOp,
     * or with status {@code 400 (Bad Request)} if the natureOp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureOp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-ops")
    public ResponseEntity<NatureOp> updateNatureOp(@RequestBody NatureOp natureOp) throws URISyntaxException {
        log.debug("REST request to update NatureOp : {}", natureOp);
        if (natureOp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NatureOp result = natureOpService.save(natureOp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureOp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nature-ops} : get all the natureOps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureOps in body.
     */
    @GetMapping("/nature-ops")
    public ResponseEntity<List<NatureOp>> getAllNatureOps(Pageable pageable) {
        log.debug("REST request to get a page of NatureOps");
        Page<NatureOp> page = natureOpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-ops/:id} : get the "id" natureOp.
     *
     * @param id the id of the natureOp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureOp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-ops/{id}")
    public ResponseEntity<NatureOp> getNatureOp(@PathVariable Long id) {
        log.debug("REST request to get NatureOp : {}", id);
        Optional<NatureOp> natureOp = natureOpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureOp);
    }

    /**
     * {@code DELETE  /nature-ops/:id} : delete the "id" natureOp.
     *
     * @param id the id of the natureOp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-ops/{id}")
    public ResponseEntity<Void> deleteNatureOp(@PathVariable Long id) {
        log.debug("REST request to delete NatureOp : {}", id);
        natureOpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
