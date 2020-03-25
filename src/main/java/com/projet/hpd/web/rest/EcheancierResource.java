package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Echeancier;
import com.projet.hpd.service.EcheancierService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Echeancier}.
 */
@RestController
@RequestMapping("/api")
public class EcheancierResource {

    private final Logger log = LoggerFactory.getLogger(EcheancierResource.class);

    private static final String ENTITY_NAME = "echeancier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcheancierService echeancierService;

    public EcheancierResource(EcheancierService echeancierService) {
        this.echeancierService = echeancierService;
    }

    /**
     * {@code POST  /echeanciers} : Create a new echeancier.
     *
     * @param echeancier the echeancier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new echeancier, or with status {@code 400 (Bad Request)} if the echeancier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/echeanciers")
    public ResponseEntity<Echeancier> createEcheancier(@RequestBody Echeancier echeancier) throws URISyntaxException {
        log.debug("REST request to save Echeancier : {}", echeancier);
        if (echeancier.getId() != null) {
            throw new BadRequestAlertException("A new echeancier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Echeancier result = echeancierService.save(echeancier);
        return ResponseEntity.created(new URI("/api/echeanciers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /echeanciers} : Updates an existing echeancier.
     *
     * @param echeancier the echeancier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated echeancier,
     * or with status {@code 400 (Bad Request)} if the echeancier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the echeancier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/echeanciers")
    public ResponseEntity<Echeancier> updateEcheancier(@RequestBody Echeancier echeancier) throws URISyntaxException {
        log.debug("REST request to update Echeancier : {}", echeancier);
        if (echeancier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Echeancier result = echeancierService.save(echeancier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, echeancier.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /echeanciers} : get all the echeanciers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of echeanciers in body.
     */
    @GetMapping("/echeanciers")
    public ResponseEntity<List<Echeancier>> getAllEcheanciers(Pageable pageable) {
        log.debug("REST request to get a page of Echeanciers");
        Page<Echeancier> page = echeancierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /echeanciers/:id} : get the "id" echeancier.
     *
     * @param id the id of the echeancier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the echeancier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/echeanciers/{id}")
    public ResponseEntity<Echeancier> getEcheancier(@PathVariable Long id) {
        log.debug("REST request to get Echeancier : {}", id);
        Optional<Echeancier> echeancier = echeancierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(echeancier);
    }

    /**
     * {@code DELETE  /echeanciers/:id} : delete the "id" echeancier.
     *
     * @param id the id of the echeancier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/echeanciers/{id}")
    public ResponseEntity<Void> deleteEcheancier(@PathVariable Long id) {
        log.debug("REST request to delete Echeancier : {}", id);
        echeancierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
