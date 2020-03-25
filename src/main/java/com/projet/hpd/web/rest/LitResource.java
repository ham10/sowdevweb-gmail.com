package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Lit;
import com.projet.hpd.service.LitService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Lit}.
 */
@RestController
@RequestMapping("/api")
public class LitResource {

    private final Logger log = LoggerFactory.getLogger(LitResource.class);

    private static final String ENTITY_NAME = "lit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LitService litService;

    public LitResource(LitService litService) {
        this.litService = litService;
    }

    /**
     * {@code POST  /lits} : Create a new lit.
     *
     * @param lit the lit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lit, or with status {@code 400 (Bad Request)} if the lit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lits")
    public ResponseEntity<Lit> createLit(@RequestBody Lit lit) throws URISyntaxException {
        log.debug("REST request to save Lit : {}", lit);
        if (lit.getId() != null) {
            throw new BadRequestAlertException("A new lit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lit result = litService.save(lit);
        return ResponseEntity.created(new URI("/api/lits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lits} : Updates an existing lit.
     *
     * @param lit the lit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lit,
     * or with status {@code 400 (Bad Request)} if the lit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lits")
    public ResponseEntity<Lit> updateLit(@RequestBody Lit lit) throws URISyntaxException {
        log.debug("REST request to update Lit : {}", lit);
        if (lit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lit result = litService.save(lit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lits} : get all the lits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lits in body.
     */
    @GetMapping("/lits")
    public ResponseEntity<List<Lit>> getAllLits(Pageable pageable) {
        log.debug("REST request to get a page of Lits");
        Page<Lit> page = litService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lits/:id} : get the "id" lit.
     *
     * @param id the id of the lit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lits/{id}")
    public ResponseEntity<Lit> getLit(@PathVariable Long id) {
        log.debug("REST request to get Lit : {}", id);
        Optional<Lit> lit = litService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lit);
    }

    /**
     * {@code DELETE  /lits/:id} : delete the "id" lit.
     *
     * @param id the id of the lit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lits/{id}")
    public ResponseEntity<Void> deleteLit(@PathVariable Long id) {
        log.debug("REST request to delete Lit : {}", id);
        litService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
