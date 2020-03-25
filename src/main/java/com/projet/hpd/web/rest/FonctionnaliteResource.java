package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Fonctionnalite;
import com.projet.hpd.service.FonctionnaliteService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Fonctionnalite}.
 */
@RestController
@RequestMapping("/api")
public class FonctionnaliteResource {

    private final Logger log = LoggerFactory.getLogger(FonctionnaliteResource.class);

    private static final String ENTITY_NAME = "fonctionnalite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FonctionnaliteService fonctionnaliteService;

    public FonctionnaliteResource(FonctionnaliteService fonctionnaliteService) {
        this.fonctionnaliteService = fonctionnaliteService;
    }

    /**
     * {@code POST  /fonctionnalites} : Create a new fonctionnalite.
     *
     * @param fonctionnalite the fonctionnalite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fonctionnalite, or with status {@code 400 (Bad Request)} if the fonctionnalite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fonctionnalites")
    public ResponseEntity<Fonctionnalite> createFonctionnalite(@RequestBody Fonctionnalite fonctionnalite) throws URISyntaxException {
        log.debug("REST request to save Fonctionnalite : {}", fonctionnalite);
        if (fonctionnalite.getId() != null) {
            throw new BadRequestAlertException("A new fonctionnalite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fonctionnalite result = fonctionnaliteService.save(fonctionnalite);
        return ResponseEntity.created(new URI("/api/fonctionnalites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fonctionnalites} : Updates an existing fonctionnalite.
     *
     * @param fonctionnalite the fonctionnalite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fonctionnalite,
     * or with status {@code 400 (Bad Request)} if the fonctionnalite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fonctionnalite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fonctionnalites")
    public ResponseEntity<Fonctionnalite> updateFonctionnalite(@RequestBody Fonctionnalite fonctionnalite) throws URISyntaxException {
        log.debug("REST request to update Fonctionnalite : {}", fonctionnalite);
        if (fonctionnalite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fonctionnalite result = fonctionnaliteService.save(fonctionnalite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fonctionnalite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fonctionnalites} : get all the fonctionnalites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fonctionnalites in body.
     */
    @GetMapping("/fonctionnalites")
    public ResponseEntity<List<Fonctionnalite>> getAllFonctionnalites(Pageable pageable) {
        log.debug("REST request to get a page of Fonctionnalites");
        Page<Fonctionnalite> page = fonctionnaliteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fonctionnalites/:id} : get the "id" fonctionnalite.
     *
     * @param id the id of the fonctionnalite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fonctionnalite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fonctionnalites/{id}")
    public ResponseEntity<Fonctionnalite> getFonctionnalite(@PathVariable Long id) {
        log.debug("REST request to get Fonctionnalite : {}", id);
        Optional<Fonctionnalite> fonctionnalite = fonctionnaliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fonctionnalite);
    }

    /**
     * {@code DELETE  /fonctionnalites/:id} : delete the "id" fonctionnalite.
     *
     * @param id the id of the fonctionnalite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fonctionnalites/{id}")
    public ResponseEntity<Void> deleteFonctionnalite(@PathVariable Long id) {
        log.debug("REST request to delete Fonctionnalite : {}", id);
        fonctionnaliteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
