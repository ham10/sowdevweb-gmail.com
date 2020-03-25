package com.projet.hpd.web.rest;

import com.projet.hpd.domain.SousFamille;
import com.projet.hpd.service.SousFamilleService;
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
 * REST controller for managing {@link com.projet.hpd.domain.SousFamille}.
 */
@RestController
@RequestMapping("/api")
public class SousFamilleResource {

    private final Logger log = LoggerFactory.getLogger(SousFamilleResource.class);

    private static final String ENTITY_NAME = "sousFamille";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SousFamilleService sousFamilleService;

    public SousFamilleResource(SousFamilleService sousFamilleService) {
        this.sousFamilleService = sousFamilleService;
    }

    /**
     * {@code POST  /sous-familles} : Create a new sousFamille.
     *
     * @param sousFamille the sousFamille to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sousFamille, or with status {@code 400 (Bad Request)} if the sousFamille has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sous-familles")
    public ResponseEntity<SousFamille> createSousFamille(@RequestBody SousFamille sousFamille) throws URISyntaxException {
        log.debug("REST request to save SousFamille : {}", sousFamille);
        if (sousFamille.getId() != null) {
            throw new BadRequestAlertException("A new sousFamille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SousFamille result = sousFamilleService.save(sousFamille);
        return ResponseEntity.created(new URI("/api/sous-familles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sous-familles} : Updates an existing sousFamille.
     *
     * @param sousFamille the sousFamille to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousFamille,
     * or with status {@code 400 (Bad Request)} if the sousFamille is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sousFamille couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sous-familles")
    public ResponseEntity<SousFamille> updateSousFamille(@RequestBody SousFamille sousFamille) throws URISyntaxException {
        log.debug("REST request to update SousFamille : {}", sousFamille);
        if (sousFamille.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SousFamille result = sousFamilleService.save(sousFamille);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sousFamille.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sous-familles} : get all the sousFamilles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sousFamilles in body.
     */
    @GetMapping("/sous-familles")
    public ResponseEntity<List<SousFamille>> getAllSousFamilles(Pageable pageable) {
        log.debug("REST request to get a page of SousFamilles");
        Page<SousFamille> page = sousFamilleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sous-familles/:id} : get the "id" sousFamille.
     *
     * @param id the id of the sousFamille to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sousFamille, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sous-familles/{id}")
    public ResponseEntity<SousFamille> getSousFamille(@PathVariable Long id) {
        log.debug("REST request to get SousFamille : {}", id);
        Optional<SousFamille> sousFamille = sousFamilleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sousFamille);
    }

    /**
     * {@code DELETE  /sous-familles/:id} : delete the "id" sousFamille.
     *
     * @param id the id of the sousFamille to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sous-familles/{id}")
    public ResponseEntity<Void> deleteSousFamille(@PathVariable Long id) {
        log.debug("REST request to delete SousFamille : {}", id);
        sousFamilleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
