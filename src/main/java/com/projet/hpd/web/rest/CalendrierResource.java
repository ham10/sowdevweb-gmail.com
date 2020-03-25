package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Calendrier;
import com.projet.hpd.service.CalendrierService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Calendrier}.
 */
@RestController
@RequestMapping("/api")
public class CalendrierResource {

    private final Logger log = LoggerFactory.getLogger(CalendrierResource.class);

    private static final String ENTITY_NAME = "calendrier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalendrierService calendrierService;

    public CalendrierResource(CalendrierService calendrierService) {
        this.calendrierService = calendrierService;
    }

    /**
     * {@code POST  /calendriers} : Create a new calendrier.
     *
     * @param calendrier the calendrier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendrier, or with status {@code 400 (Bad Request)} if the calendrier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calendriers")
    public ResponseEntity<Calendrier> createCalendrier(@RequestBody Calendrier calendrier) throws URISyntaxException {
        log.debug("REST request to save Calendrier : {}", calendrier);
        if (calendrier.getId() != null) {
            throw new BadRequestAlertException("A new calendrier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Calendrier result = calendrierService.save(calendrier);
        return ResponseEntity.created(new URI("/api/calendriers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calendriers} : Updates an existing calendrier.
     *
     * @param calendrier the calendrier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendrier,
     * or with status {@code 400 (Bad Request)} if the calendrier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendrier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calendriers")
    public ResponseEntity<Calendrier> updateCalendrier(@RequestBody Calendrier calendrier) throws URISyntaxException {
        log.debug("REST request to update Calendrier : {}", calendrier);
        if (calendrier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Calendrier result = calendrierService.save(calendrier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calendrier.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calendriers} : get all the calendriers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calendriers in body.
     */
    @GetMapping("/calendriers")
    public ResponseEntity<List<Calendrier>> getAllCalendriers(Pageable pageable) {
        log.debug("REST request to get a page of Calendriers");
        Page<Calendrier> page = calendrierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calendriers/:id} : get the "id" calendrier.
     *
     * @param id the id of the calendrier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendrier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendriers/{id}")
    public ResponseEntity<Calendrier> getCalendrier(@PathVariable Long id) {
        log.debug("REST request to get Calendrier : {}", id);
        Optional<Calendrier> calendrier = calendrierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendrier);
    }

    /**
     * {@code DELETE  /calendriers/:id} : delete the "id" calendrier.
     *
     * @param id the id of the calendrier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calendriers/{id}")
    public ResponseEntity<Void> deleteCalendrier(@PathVariable Long id) {
        log.debug("REST request to delete Calendrier : {}", id);
        calendrierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
