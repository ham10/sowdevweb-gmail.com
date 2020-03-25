package com.projet.hpd.web.rest;

import com.projet.hpd.domain.JourFerie;
import com.projet.hpd.service.JourFerieService;
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
 * REST controller for managing {@link com.projet.hpd.domain.JourFerie}.
 */
@RestController
@RequestMapping("/api")
public class JourFerieResource {

    private final Logger log = LoggerFactory.getLogger(JourFerieResource.class);

    private static final String ENTITY_NAME = "jourFerie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourFerieService jourFerieService;

    public JourFerieResource(JourFerieService jourFerieService) {
        this.jourFerieService = jourFerieService;
    }

    /**
     * {@code POST  /jour-feries} : Create a new jourFerie.
     *
     * @param jourFerie the jourFerie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jourFerie, or with status {@code 400 (Bad Request)} if the jourFerie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jour-feries")
    public ResponseEntity<JourFerie> createJourFerie(@RequestBody JourFerie jourFerie) throws URISyntaxException {
        log.debug("REST request to save JourFerie : {}", jourFerie);
        if (jourFerie.getId() != null) {
            throw new BadRequestAlertException("A new jourFerie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourFerie result = jourFerieService.save(jourFerie);
        return ResponseEntity.created(new URI("/api/jour-feries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jour-feries} : Updates an existing jourFerie.
     *
     * @param jourFerie the jourFerie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jourFerie,
     * or with status {@code 400 (Bad Request)} if the jourFerie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jourFerie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jour-feries")
    public ResponseEntity<JourFerie> updateJourFerie(@RequestBody JourFerie jourFerie) throws URISyntaxException {
        log.debug("REST request to update JourFerie : {}", jourFerie);
        if (jourFerie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JourFerie result = jourFerieService.save(jourFerie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jourFerie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jour-feries} : get all the jourFeries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jourFeries in body.
     */
    @GetMapping("/jour-feries")
    public ResponseEntity<List<JourFerie>> getAllJourFeries(Pageable pageable) {
        log.debug("REST request to get a page of JourFeries");
        Page<JourFerie> page = jourFerieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jour-feries/:id} : get the "id" jourFerie.
     *
     * @param id the id of the jourFerie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jourFerie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jour-feries/{id}")
    public ResponseEntity<JourFerie> getJourFerie(@PathVariable Long id) {
        log.debug("REST request to get JourFerie : {}", id);
        Optional<JourFerie> jourFerie = jourFerieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jourFerie);
    }

    /**
     * {@code DELETE  /jour-feries/:id} : delete the "id" jourFerie.
     *
     * @param id the id of the jourFerie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jour-feries/{id}")
    public ResponseEntity<Void> deleteJourFerie(@PathVariable Long id) {
        log.debug("REST request to delete JourFerie : {}", id);
        jourFerieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
