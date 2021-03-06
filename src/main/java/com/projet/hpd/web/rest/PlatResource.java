package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Plat;
import com.projet.hpd.service.PlatService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Plat}.
 */
@RestController
@RequestMapping("/api")
public class PlatResource {

    private final Logger log = LoggerFactory.getLogger(PlatResource.class);

    private static final String ENTITY_NAME = "plat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlatService platService;

    public PlatResource(PlatService platService) {
        this.platService = platService;
    }

    /**
     * {@code POST  /plats} : Create a new plat.
     *
     * @param plat the plat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plat, or with status {@code 400 (Bad Request)} if the plat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plats")
    public ResponseEntity<Plat> createPlat(@RequestBody Plat plat) throws URISyntaxException {
        log.debug("REST request to save Plat : {}", plat);
        if (plat.getId() != null) {
            throw new BadRequestAlertException("A new plat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plat result = platService.save(plat);
        return ResponseEntity.created(new URI("/api/plats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plats} : Updates an existing plat.
     *
     * @param plat the plat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plat,
     * or with status {@code 400 (Bad Request)} if the plat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plats")
    public ResponseEntity<Plat> updatePlat(@RequestBody Plat plat) throws URISyntaxException {
        log.debug("REST request to update Plat : {}", plat);
        if (plat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Plat result = platService.save(plat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plats} : get all the plats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plats in body.
     */
    @GetMapping("/plats")
    public ResponseEntity<List<Plat>> getAllPlats(Pageable pageable) {
        log.debug("REST request to get a page of Plats");
        Page<Plat> page = platService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plats/:id} : get the "id" plat.
     *
     * @param id the id of the plat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plats/{id}")
    public ResponseEntity<Plat> getPlat(@PathVariable Long id) {
        log.debug("REST request to get Plat : {}", id);
        Optional<Plat> plat = platService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plat);
    }

    /**
     * {@code DELETE  /plats/:id} : delete the "id" plat.
     *
     * @param id the id of the plat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plats/{id}")
    public ResponseEntity<Void> deletePlat(@PathVariable Long id) {
        log.debug("REST request to delete Plat : {}", id);
        platService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
