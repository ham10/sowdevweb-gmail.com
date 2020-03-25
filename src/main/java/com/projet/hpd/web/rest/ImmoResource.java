package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Immo;
import com.projet.hpd.service.ImmoService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Immo}.
 */
@RestController
@RequestMapping("/api")
public class ImmoResource {

    private final Logger log = LoggerFactory.getLogger(ImmoResource.class);

    private static final String ENTITY_NAME = "immo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImmoService immoService;

    public ImmoResource(ImmoService immoService) {
        this.immoService = immoService;
    }

    /**
     * {@code POST  /immos} : Create a new immo.
     *
     * @param immo the immo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new immo, or with status {@code 400 (Bad Request)} if the immo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/immos")
    public ResponseEntity<Immo> createImmo(@RequestBody Immo immo) throws URISyntaxException {
        log.debug("REST request to save Immo : {}", immo);
        if (immo.getId() != null) {
            throw new BadRequestAlertException("A new immo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Immo result = immoService.save(immo);
        return ResponseEntity.created(new URI("/api/immos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /immos} : Updates an existing immo.
     *
     * @param immo the immo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated immo,
     * or with status {@code 400 (Bad Request)} if the immo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the immo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/immos")
    public ResponseEntity<Immo> updateImmo(@RequestBody Immo immo) throws URISyntaxException {
        log.debug("REST request to update Immo : {}", immo);
        if (immo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Immo result = immoService.save(immo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /immos} : get all the immos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of immos in body.
     */
    @GetMapping("/immos")
    public ResponseEntity<List<Immo>> getAllImmos(Pageable pageable) {
        log.debug("REST request to get a page of Immos");
        Page<Immo> page = immoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /immos/:id} : get the "id" immo.
     *
     * @param id the id of the immo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the immo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/immos/{id}")
    public ResponseEntity<Immo> getImmo(@PathVariable Long id) {
        log.debug("REST request to get Immo : {}", id);
        Optional<Immo> immo = immoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(immo);
    }

    /**
     * {@code DELETE  /immos/:id} : delete the "id" immo.
     *
     * @param id the id of the immo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/immos/{id}")
    public ResponseEntity<Void> deleteImmo(@PathVariable Long id) {
        log.debug("REST request to delete Immo : {}", id);
        immoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
