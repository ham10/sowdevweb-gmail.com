package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Jour;
import com.projet.hpd.service.JourService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Jour}.
 */
@RestController
@RequestMapping("/api")
public class JourResource {

    private final Logger log = LoggerFactory.getLogger(JourResource.class);

    private static final String ENTITY_NAME = "jour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourService jourService;

    public JourResource(JourService jourService) {
        this.jourService = jourService;
    }

    /**
     * {@code POST  /jours} : Create a new jour.
     *
     * @param jour the jour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jour, or with status {@code 400 (Bad Request)} if the jour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jours")
    public ResponseEntity<Jour> createJour(@RequestBody Jour jour) throws URISyntaxException {
        log.debug("REST request to save Jour : {}", jour);
        if (jour.getId() != null) {
            throw new BadRequestAlertException("A new jour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jour result = jourService.save(jour);
        return ResponseEntity.created(new URI("/api/jours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jours} : Updates an existing jour.
     *
     * @param jour the jour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jour,
     * or with status {@code 400 (Bad Request)} if the jour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jours")
    public ResponseEntity<Jour> updateJour(@RequestBody Jour jour) throws URISyntaxException {
        log.debug("REST request to update Jour : {}", jour);
        if (jour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Jour result = jourService.save(jour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jour.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jours} : get all the jours.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jours in body.
     */
    @GetMapping("/jours")
    public ResponseEntity<List<Jour>> getAllJours(Pageable pageable) {
        log.debug("REST request to get a page of Jours");
        Page<Jour> page = jourService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jours/:id} : get the "id" jour.
     *
     * @param id the id of the jour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jours/{id}")
    public ResponseEntity<Jour> getJour(@PathVariable Long id) {
        log.debug("REST request to get Jour : {}", id);
        Optional<Jour> jour = jourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jour);
    }

    /**
     * {@code DELETE  /jours/:id} : delete the "id" jour.
     *
     * @param id the id of the jour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jours/{id}")
    public ResponseEntity<Void> deleteJour(@PathVariable Long id) {
        log.debug("REST request to delete Jour : {}", id);
        jourService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
