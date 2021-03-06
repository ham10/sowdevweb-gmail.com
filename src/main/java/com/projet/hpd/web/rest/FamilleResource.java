package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Famille;
import com.projet.hpd.service.FamilleService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Famille}.
 */
@RestController
@RequestMapping("/api")
public class FamilleResource {

    private final Logger log = LoggerFactory.getLogger(FamilleResource.class);

    private static final String ENTITY_NAME = "famille";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilleService familleService;

    public FamilleResource(FamilleService familleService) {
        this.familleService = familleService;
    }

    /**
     * {@code POST  /familles} : Create a new famille.
     *
     * @param famille the famille to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new famille, or with status {@code 400 (Bad Request)} if the famille has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/familles")
    public ResponseEntity<Famille> createFamille(@RequestBody Famille famille) throws URISyntaxException {
        log.debug("REST request to save Famille : {}", famille);
        if (famille.getId() != null) {
            throw new BadRequestAlertException("A new famille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Famille result = familleService.save(famille);
        return ResponseEntity.created(new URI("/api/familles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /familles} : Updates an existing famille.
     *
     * @param famille the famille to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated famille,
     * or with status {@code 400 (Bad Request)} if the famille is not valid,
     * or with status {@code 500 (Internal Server Error)} if the famille couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/familles")
    public ResponseEntity<Famille> updateFamille(@RequestBody Famille famille) throws URISyntaxException {
        log.debug("REST request to update Famille : {}", famille);
        if (famille.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Famille result = familleService.save(famille);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, famille.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /familles} : get all the familles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familles in body.
     */
    @GetMapping("/familles")
    public ResponseEntity<List<Famille>> getAllFamilles(Pageable pageable) {
        log.debug("REST request to get a page of Familles");
        Page<Famille> page = familleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /familles/:id} : get the "id" famille.
     *
     * @param id the id of the famille to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the famille, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/familles/{id}")
    public ResponseEntity<Famille> getFamille(@PathVariable Long id) {
        log.debug("REST request to get Famille : {}", id);
        Optional<Famille> famille = familleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(famille);
    }

    /**
     * {@code DELETE  /familles/:id} : delete the "id" famille.
     *
     * @param id the id of the famille to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/familles/{id}")
    public ResponseEntity<Void> deleteFamille(@PathVariable Long id) {
        log.debug("REST request to delete Famille : {}", id);
        familleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
