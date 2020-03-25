package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Cible;
import com.projet.hpd.service.CibleService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Cible}.
 */
@RestController
@RequestMapping("/api")
public class CibleResource {

    private final Logger log = LoggerFactory.getLogger(CibleResource.class);

    private static final String ENTITY_NAME = "cible";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CibleService cibleService;

    public CibleResource(CibleService cibleService) {
        this.cibleService = cibleService;
    }

    /**
     * {@code POST  /cibles} : Create a new cible.
     *
     * @param cible the cible to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cible, or with status {@code 400 (Bad Request)} if the cible has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cibles")
    public ResponseEntity<Cible> createCible(@RequestBody Cible cible) throws URISyntaxException {
        log.debug("REST request to save Cible : {}", cible);
        if (cible.getId() != null) {
            throw new BadRequestAlertException("A new cible cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cible result = cibleService.save(cible);
        return ResponseEntity.created(new URI("/api/cibles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cibles} : Updates an existing cible.
     *
     * @param cible the cible to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cible,
     * or with status {@code 400 (Bad Request)} if the cible is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cible couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cibles")
    public ResponseEntity<Cible> updateCible(@RequestBody Cible cible) throws URISyntaxException {
        log.debug("REST request to update Cible : {}", cible);
        if (cible.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cible result = cibleService.save(cible);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cible.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cibles} : get all the cibles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cibles in body.
     */
    @GetMapping("/cibles")
    public ResponseEntity<List<Cible>> getAllCibles(Pageable pageable) {
        log.debug("REST request to get a page of Cibles");
        Page<Cible> page = cibleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cibles/:id} : get the "id" cible.
     *
     * @param id the id of the cible to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cible, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cibles/{id}")
    public ResponseEntity<Cible> getCible(@PathVariable Long id) {
        log.debug("REST request to get Cible : {}", id);
        Optional<Cible> cible = cibleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cible);
    }

    /**
     * {@code DELETE  /cibles/:id} : delete the "id" cible.
     *
     * @param id the id of the cible to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cibles/{id}")
    public ResponseEntity<Void> deleteCible(@PathVariable Long id) {
        log.debug("REST request to delete Cible : {}", id);
        cibleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
