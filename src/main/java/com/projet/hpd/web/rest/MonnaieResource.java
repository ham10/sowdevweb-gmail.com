package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Monnaie;
import com.projet.hpd.service.MonnaieService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Monnaie}.
 */
@RestController
@RequestMapping("/api")
public class MonnaieResource {

    private final Logger log = LoggerFactory.getLogger(MonnaieResource.class);

    private static final String ENTITY_NAME = "monnaie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonnaieService monnaieService;

    public MonnaieResource(MonnaieService monnaieService) {
        this.monnaieService = monnaieService;
    }

    /**
     * {@code POST  /monnaies} : Create a new monnaie.
     *
     * @param monnaie the monnaie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monnaie, or with status {@code 400 (Bad Request)} if the monnaie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monnaies")
    public ResponseEntity<Monnaie> createMonnaie(@RequestBody Monnaie monnaie) throws URISyntaxException {
        log.debug("REST request to save Monnaie : {}", monnaie);
        if (monnaie.getId() != null) {
            throw new BadRequestAlertException("A new monnaie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Monnaie result = monnaieService.save(monnaie);
        return ResponseEntity.created(new URI("/api/monnaies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monnaies} : Updates an existing monnaie.
     *
     * @param monnaie the monnaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monnaie,
     * or with status {@code 400 (Bad Request)} if the monnaie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monnaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monnaies")
    public ResponseEntity<Monnaie> updateMonnaie(@RequestBody Monnaie monnaie) throws URISyntaxException {
        log.debug("REST request to update Monnaie : {}", monnaie);
        if (monnaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Monnaie result = monnaieService.save(monnaie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monnaie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /monnaies} : get all the monnaies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monnaies in body.
     */
    @GetMapping("/monnaies")
    public ResponseEntity<List<Monnaie>> getAllMonnaies(Pageable pageable) {
        log.debug("REST request to get a page of Monnaies");
        Page<Monnaie> page = monnaieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /monnaies/:id} : get the "id" monnaie.
     *
     * @param id the id of the monnaie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monnaie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monnaies/{id}")
    public ResponseEntity<Monnaie> getMonnaie(@PathVariable Long id) {
        log.debug("REST request to get Monnaie : {}", id);
        Optional<Monnaie> monnaie = monnaieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monnaie);
    }

    /**
     * {@code DELETE  /monnaies/:id} : delete the "id" monnaie.
     *
     * @param id the id of the monnaie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monnaies/{id}")
    public ResponseEntity<Void> deleteMonnaie(@PathVariable Long id) {
        log.debug("REST request to delete Monnaie : {}", id);
        monnaieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
