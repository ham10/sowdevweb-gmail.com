package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Evenement;
import com.projet.hpd.service.EvenementService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Evenement}.
 */
@RestController
@RequestMapping("/api")
public class EvenementResource {

    private final Logger log = LoggerFactory.getLogger(EvenementResource.class);

    private static final String ENTITY_NAME = "evenement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvenementService evenementService;

    public EvenementResource(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    /**
     * {@code POST  /evenements} : Create a new evenement.
     *
     * @param evenement the evenement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evenement, or with status {@code 400 (Bad Request)} if the evenement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evenements")
    public ResponseEntity<Evenement> createEvenement(@RequestBody Evenement evenement) throws URISyntaxException {
        log.debug("REST request to save Evenement : {}", evenement);
        if (evenement.getId() != null) {
            throw new BadRequestAlertException("A new evenement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Evenement result = evenementService.save(evenement);
        return ResponseEntity.created(new URI("/api/evenements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evenements} : Updates an existing evenement.
     *
     * @param evenement the evenement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evenement,
     * or with status {@code 400 (Bad Request)} if the evenement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evenement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evenements")
    public ResponseEntity<Evenement> updateEvenement(@RequestBody Evenement evenement) throws URISyntaxException {
        log.debug("REST request to update Evenement : {}", evenement);
        if (evenement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Evenement result = evenementService.save(evenement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evenement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evenements} : get all the evenements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evenements in body.
     */
    @GetMapping("/evenements")
    public ResponseEntity<List<Evenement>> getAllEvenements(Pageable pageable) {
        log.debug("REST request to get a page of Evenements");
        Page<Evenement> page = evenementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evenements/:id} : get the "id" evenement.
     *
     * @param id the id of the evenement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evenement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evenements/{id}")
    public ResponseEntity<Evenement> getEvenement(@PathVariable Long id) {
        log.debug("REST request to get Evenement : {}", id);
        Optional<Evenement> evenement = evenementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evenement);
    }

    /**
     * {@code DELETE  /evenements/:id} : delete the "id" evenement.
     *
     * @param id the id of the evenement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evenements/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        log.debug("REST request to delete Evenement : {}", id);
        evenementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
