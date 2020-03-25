package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Antecedent;
import com.projet.hpd.service.AntecedentService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Antecedent}.
 */
@RestController
@RequestMapping("/api")
public class AntecedentResource {

    private final Logger log = LoggerFactory.getLogger(AntecedentResource.class);

    private static final String ENTITY_NAME = "antecedent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AntecedentService antecedentService;

    public AntecedentResource(AntecedentService antecedentService) {
        this.antecedentService = antecedentService;
    }

    /**
     * {@code POST  /antecedents} : Create a new antecedent.
     *
     * @param antecedent the antecedent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new antecedent, or with status {@code 400 (Bad Request)} if the antecedent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/antecedents")
    public ResponseEntity<Antecedent> createAntecedent(@RequestBody Antecedent antecedent) throws URISyntaxException {
        log.debug("REST request to save Antecedent : {}", antecedent);
        if (antecedent.getId() != null) {
            throw new BadRequestAlertException("A new antecedent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Antecedent result = antecedentService.save(antecedent);
        return ResponseEntity.created(new URI("/api/antecedents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /antecedents} : Updates an existing antecedent.
     *
     * @param antecedent the antecedent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antecedent,
     * or with status {@code 400 (Bad Request)} if the antecedent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the antecedent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/antecedents")
    public ResponseEntity<Antecedent> updateAntecedent(@RequestBody Antecedent antecedent) throws URISyntaxException {
        log.debug("REST request to update Antecedent : {}", antecedent);
        if (antecedent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Antecedent result = antecedentService.save(antecedent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antecedent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /antecedents} : get all the antecedents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of antecedents in body.
     */
    @GetMapping("/antecedents")
    public ResponseEntity<List<Antecedent>> getAllAntecedents(Pageable pageable) {
        log.debug("REST request to get a page of Antecedents");
        Page<Antecedent> page = antecedentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /antecedents/:id} : get the "id" antecedent.
     *
     * @param id the id of the antecedent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the antecedent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/antecedents/{id}")
    public ResponseEntity<Antecedent> getAntecedent(@PathVariable Long id) {
        log.debug("REST request to get Antecedent : {}", id);
        Optional<Antecedent> antecedent = antecedentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(antecedent);
    }

    /**
     * {@code DELETE  /antecedents/:id} : delete the "id" antecedent.
     *
     * @param id the id of the antecedent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/antecedents/{id}")
    public ResponseEntity<Void> deleteAntecedent(@PathVariable Long id) {
        log.debug("REST request to delete Antecedent : {}", id);
        antecedentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
