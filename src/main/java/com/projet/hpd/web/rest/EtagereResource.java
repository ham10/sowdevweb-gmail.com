package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Etagere;
import com.projet.hpd.service.EtagereService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Etagere}.
 */
@RestController
@RequestMapping("/api")
public class EtagereResource {

    private final Logger log = LoggerFactory.getLogger(EtagereResource.class);

    private static final String ENTITY_NAME = "etagere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtagereService etagereService;

    public EtagereResource(EtagereService etagereService) {
        this.etagereService = etagereService;
    }

    /**
     * {@code POST  /etageres} : Create a new etagere.
     *
     * @param etagere the etagere to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etagere, or with status {@code 400 (Bad Request)} if the etagere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etageres")
    public ResponseEntity<Etagere> createEtagere(@RequestBody Etagere etagere) throws URISyntaxException {
        log.debug("REST request to save Etagere : {}", etagere);
        if (etagere.getId() != null) {
            throw new BadRequestAlertException("A new etagere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etagere result = etagereService.save(etagere);
        return ResponseEntity.created(new URI("/api/etageres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etageres} : Updates an existing etagere.
     *
     * @param etagere the etagere to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etagere,
     * or with status {@code 400 (Bad Request)} if the etagere is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etagere couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etageres")
    public ResponseEntity<Etagere> updateEtagere(@RequestBody Etagere etagere) throws URISyntaxException {
        log.debug("REST request to update Etagere : {}", etagere);
        if (etagere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Etagere result = etagereService.save(etagere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etagere.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etageres} : get all the etageres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etageres in body.
     */
    @GetMapping("/etageres")
    public ResponseEntity<List<Etagere>> getAllEtageres(Pageable pageable) {
        log.debug("REST request to get a page of Etageres");
        Page<Etagere> page = etagereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etageres/:id} : get the "id" etagere.
     *
     * @param id the id of the etagere to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etagere, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etageres/{id}")
    public ResponseEntity<Etagere> getEtagere(@PathVariable Long id) {
        log.debug("REST request to get Etagere : {}", id);
        Optional<Etagere> etagere = etagereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etagere);
    }

    /**
     * {@code DELETE  /etageres/:id} : delete the "id" etagere.
     *
     * @param id the id of the etagere to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etageres/{id}")
    public ResponseEntity<Void> deleteEtagere(@PathVariable Long id) {
        log.debug("REST request to delete Etagere : {}", id);
        etagereService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
