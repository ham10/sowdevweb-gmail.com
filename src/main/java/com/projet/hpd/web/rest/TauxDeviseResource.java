package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TauxDevise;
import com.projet.hpd.service.TauxDeviseService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TauxDevise}.
 */
@RestController
@RequestMapping("/api")
public class TauxDeviseResource {

    private final Logger log = LoggerFactory.getLogger(TauxDeviseResource.class);

    private static final String ENTITY_NAME = "tauxDevise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TauxDeviseService tauxDeviseService;

    public TauxDeviseResource(TauxDeviseService tauxDeviseService) {
        this.tauxDeviseService = tauxDeviseService;
    }

    /**
     * {@code POST  /taux-devises} : Create a new tauxDevise.
     *
     * @param tauxDevise the tauxDevise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tauxDevise, or with status {@code 400 (Bad Request)} if the tauxDevise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taux-devises")
    public ResponseEntity<TauxDevise> createTauxDevise(@RequestBody TauxDevise tauxDevise) throws URISyntaxException {
        log.debug("REST request to save TauxDevise : {}", tauxDevise);
        if (tauxDevise.getId() != null) {
            throw new BadRequestAlertException("A new tauxDevise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TauxDevise result = tauxDeviseService.save(tauxDevise);
        return ResponseEntity.created(new URI("/api/taux-devises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taux-devises} : Updates an existing tauxDevise.
     *
     * @param tauxDevise the tauxDevise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tauxDevise,
     * or with status {@code 400 (Bad Request)} if the tauxDevise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tauxDevise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taux-devises")
    public ResponseEntity<TauxDevise> updateTauxDevise(@RequestBody TauxDevise tauxDevise) throws URISyntaxException {
        log.debug("REST request to update TauxDevise : {}", tauxDevise);
        if (tauxDevise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TauxDevise result = tauxDeviseService.save(tauxDevise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tauxDevise.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /taux-devises} : get all the tauxDevises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tauxDevises in body.
     */
    @GetMapping("/taux-devises")
    public ResponseEntity<List<TauxDevise>> getAllTauxDevises(Pageable pageable) {
        log.debug("REST request to get a page of TauxDevises");
        Page<TauxDevise> page = tauxDeviseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /taux-devises/:id} : get the "id" tauxDevise.
     *
     * @param id the id of the tauxDevise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tauxDevise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taux-devises/{id}")
    public ResponseEntity<TauxDevise> getTauxDevise(@PathVariable Long id) {
        log.debug("REST request to get TauxDevise : {}", id);
        Optional<TauxDevise> tauxDevise = tauxDeviseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tauxDevise);
    }

    /**
     * {@code DELETE  /taux-devises/:id} : delete the "id" tauxDevise.
     *
     * @param id the id of the tauxDevise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taux-devises/{id}")
    public ResponseEntity<Void> deleteTauxDevise(@PathVariable Long id) {
        log.debug("REST request to delete TauxDevise : {}", id);
        tauxDeviseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
