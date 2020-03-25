package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Civilite;
import com.projet.hpd.service.CiviliteService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Civilite}.
 */
@RestController
@RequestMapping("/api")
public class CiviliteResource {

    private final Logger log = LoggerFactory.getLogger(CiviliteResource.class);

    private static final String ENTITY_NAME = "civilite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CiviliteService civiliteService;

    public CiviliteResource(CiviliteService civiliteService) {
        this.civiliteService = civiliteService;
    }

    /**
     * {@code POST  /civilites} : Create a new civilite.
     *
     * @param civilite the civilite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new civilite, or with status {@code 400 (Bad Request)} if the civilite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/civilites")
    public ResponseEntity<Civilite> createCivilite(@RequestBody Civilite civilite) throws URISyntaxException {
        log.debug("REST request to save Civilite : {}", civilite);
        if (civilite.getId() != null) {
            throw new BadRequestAlertException("A new civilite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Civilite result = civiliteService.save(civilite);
        return ResponseEntity.created(new URI("/api/civilites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /civilites} : Updates an existing civilite.
     *
     * @param civilite the civilite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated civilite,
     * or with status {@code 400 (Bad Request)} if the civilite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the civilite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/civilites")
    public ResponseEntity<Civilite> updateCivilite(@RequestBody Civilite civilite) throws URISyntaxException {
        log.debug("REST request to update Civilite : {}", civilite);
        if (civilite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Civilite result = civiliteService.save(civilite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, civilite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /civilites} : get all the civilites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of civilites in body.
     */
    @GetMapping("/civilites")
    public ResponseEntity<List<Civilite>> getAllCivilites(Pageable pageable) {
        log.debug("REST request to get a page of Civilites");
        Page<Civilite> page = civiliteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /civilites/:id} : get the "id" civilite.
     *
     * @param id the id of the civilite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the civilite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/civilites/{id}")
    public ResponseEntity<Civilite> getCivilite(@PathVariable Long id) {
        log.debug("REST request to get Civilite : {}", id);
        Optional<Civilite> civilite = civiliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(civilite);
    }

    /**
     * {@code DELETE  /civilites/:id} : delete the "id" civilite.
     *
     * @param id the id of the civilite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/civilites/{id}")
    public ResponseEntity<Void> deleteCivilite(@PathVariable Long id) {
        log.debug("REST request to delete Civilite : {}", id);
        civiliteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
