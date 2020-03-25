package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Ordonnance;
import com.projet.hpd.service.OrdonnanceService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Ordonnance}.
 */
@RestController
@RequestMapping("/api")
public class OrdonnanceResource {

    private final Logger log = LoggerFactory.getLogger(OrdonnanceResource.class);

    private static final String ENTITY_NAME = "ordonnance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdonnanceService ordonnanceService;

    public OrdonnanceResource(OrdonnanceService ordonnanceService) {
        this.ordonnanceService = ordonnanceService;
    }

    /**
     * {@code POST  /ordonnances} : Create a new ordonnance.
     *
     * @param ordonnance the ordonnance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordonnance, or with status {@code 400 (Bad Request)} if the ordonnance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ordonnances")
    public ResponseEntity<Ordonnance> createOrdonnance(@RequestBody Ordonnance ordonnance) throws URISyntaxException {
        log.debug("REST request to save Ordonnance : {}", ordonnance);
        if (ordonnance.getId() != null) {
            throw new BadRequestAlertException("A new ordonnance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ordonnance result = ordonnanceService.save(ordonnance);
        return ResponseEntity.created(new URI("/api/ordonnances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ordonnances} : Updates an existing ordonnance.
     *
     * @param ordonnance the ordonnance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordonnance,
     * or with status {@code 400 (Bad Request)} if the ordonnance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordonnance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ordonnances")
    public ResponseEntity<Ordonnance> updateOrdonnance(@RequestBody Ordonnance ordonnance) throws URISyntaxException {
        log.debug("REST request to update Ordonnance : {}", ordonnance);
        if (ordonnance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ordonnance result = ordonnanceService.save(ordonnance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordonnance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ordonnances} : get all the ordonnances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordonnances in body.
     */
    @GetMapping("/ordonnances")
    public ResponseEntity<List<Ordonnance>> getAllOrdonnances(Pageable pageable) {
        log.debug("REST request to get a page of Ordonnances");
        Page<Ordonnance> page = ordonnanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ordonnances/:id} : get the "id" ordonnance.
     *
     * @param id the id of the ordonnance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordonnance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ordonnances/{id}")
    public ResponseEntity<Ordonnance> getOrdonnance(@PathVariable Long id) {
        log.debug("REST request to get Ordonnance : {}", id);
        Optional<Ordonnance> ordonnance = ordonnanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordonnance);
    }

    /**
     * {@code DELETE  /ordonnances/:id} : delete the "id" ordonnance.
     *
     * @param id the id of the ordonnance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ordonnances/{id}")
    public ResponseEntity<Void> deleteOrdonnance(@PathVariable Long id) {
        log.debug("REST request to delete Ordonnance : {}", id);
        ordonnanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
