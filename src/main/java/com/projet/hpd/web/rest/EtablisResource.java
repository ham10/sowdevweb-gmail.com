package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Etablis;
import com.projet.hpd.service.EtablisService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Etablis}.
 */
@RestController
@RequestMapping("/api")
public class EtablisResource {

    private final Logger log = LoggerFactory.getLogger(EtablisResource.class);

    private static final String ENTITY_NAME = "etablis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtablisService etablisService;

    public EtablisResource(EtablisService etablisService) {
        this.etablisService = etablisService;
    }

    /**
     * {@code POST  /etablis} : Create a new etablis.
     *
     * @param etablis the etablis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etablis, or with status {@code 400 (Bad Request)} if the etablis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etablis")
    public ResponseEntity<Etablis> createEtablis(@RequestBody Etablis etablis) throws URISyntaxException {
        log.debug("REST request to save Etablis : {}", etablis);
        if (etablis.getId() != null) {
            throw new BadRequestAlertException("A new etablis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etablis result = etablisService.save(etablis);
        return ResponseEntity.created(new URI("/api/etablis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etablis} : Updates an existing etablis.
     *
     * @param etablis the etablis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablis,
     * or with status {@code 400 (Bad Request)} if the etablis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etablis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etablis")
    public ResponseEntity<Etablis> updateEtablis(@RequestBody Etablis etablis) throws URISyntaxException {
        log.debug("REST request to update Etablis : {}", etablis);
        if (etablis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Etablis result = etablisService.save(etablis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etablis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etablis} : get all the etablis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etablis in body.
     */
    @GetMapping("/etablis")
    public ResponseEntity<List<Etablis>> getAllEtablis(Pageable pageable) {
        log.debug("REST request to get a page of Etablis");
        Page<Etablis> page = etablisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etablis/:id} : get the "id" etablis.
     *
     * @param id the id of the etablis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etablis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etablis/{id}")
    public ResponseEntity<Etablis> getEtablis(@PathVariable Long id) {
        log.debug("REST request to get Etablis : {}", id);
        Optional<Etablis> etablis = etablisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etablis);
    }

    /**
     * {@code DELETE  /etablis/:id} : delete the "id" etablis.
     *
     * @param id the id of the etablis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etablis/{id}")
    public ResponseEntity<Void> deleteEtablis(@PathVariable Long id) {
        log.debug("REST request to delete Etablis : {}", id);
        etablisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
