package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Tarif;
import com.projet.hpd.service.TarifService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Tarif}.
 */
@RestController
@RequestMapping("/api")
public class TarifResource {

    private final Logger log = LoggerFactory.getLogger(TarifResource.class);

    private static final String ENTITY_NAME = "tarif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifService tarifService;

    public TarifResource(TarifService tarifService) {
        this.tarifService = tarifService;
    }

    /**
     * {@code POST  /tarifs} : Create a new tarif.
     *
     * @param tarif the tarif to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarif, or with status {@code 400 (Bad Request)} if the tarif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarifs")
    public ResponseEntity<Tarif> createTarif(@RequestBody Tarif tarif) throws URISyntaxException {
        log.debug("REST request to save Tarif : {}", tarif);
        if (tarif.getId() != null) {
            throw new BadRequestAlertException("A new tarif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tarif result = tarifService.save(tarif);
        return ResponseEntity.created(new URI("/api/tarifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarifs} : Updates an existing tarif.
     *
     * @param tarif the tarif to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarif,
     * or with status {@code 400 (Bad Request)} if the tarif is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarif couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarifs")
    public ResponseEntity<Tarif> updateTarif(@RequestBody Tarif tarif) throws URISyntaxException {
        log.debug("REST request to update Tarif : {}", tarif);
        if (tarif.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tarif result = tarifService.save(tarif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarif.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarifs} : get all the tarifs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifs in body.
     */
    @GetMapping("/tarifs")
    public ResponseEntity<List<Tarif>> getAllTarifs(Pageable pageable) {
        log.debug("REST request to get a page of Tarifs");
        Page<Tarif> page = tarifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarifs/:id} : get the "id" tarif.
     *
     * @param id the id of the tarif to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarif, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarifs/{id}")
    public ResponseEntity<Tarif> getTarif(@PathVariable Long id) {
        log.debug("REST request to get Tarif : {}", id);
        Optional<Tarif> tarif = tarifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarif);
    }

    /**
     * {@code DELETE  /tarifs/:id} : delete the "id" tarif.
     *
     * @param id the id of the tarif to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarifs/{id}")
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        log.debug("REST request to delete Tarif : {}", id);
        tarifService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
