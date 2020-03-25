package com.projet.hpd.web.rest;

import com.projet.hpd.domain.EtatFacture;
import com.projet.hpd.service.EtatFactureService;
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
 * REST controller for managing {@link com.projet.hpd.domain.EtatFacture}.
 */
@RestController
@RequestMapping("/api")
public class EtatFactureResource {

    private final Logger log = LoggerFactory.getLogger(EtatFactureResource.class);

    private static final String ENTITY_NAME = "etatFacture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatFactureService etatFactureService;

    public EtatFactureResource(EtatFactureService etatFactureService) {
        this.etatFactureService = etatFactureService;
    }

    /**
     * {@code POST  /etat-factures} : Create a new etatFacture.
     *
     * @param etatFacture the etatFacture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatFacture, or with status {@code 400 (Bad Request)} if the etatFacture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-factures")
    public ResponseEntity<EtatFacture> createEtatFacture(@RequestBody EtatFacture etatFacture) throws URISyntaxException {
        log.debug("REST request to save EtatFacture : {}", etatFacture);
        if (etatFacture.getId() != null) {
            throw new BadRequestAlertException("A new etatFacture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatFacture result = etatFactureService.save(etatFacture);
        return ResponseEntity.created(new URI("/api/etat-factures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-factures} : Updates an existing etatFacture.
     *
     * @param etatFacture the etatFacture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatFacture,
     * or with status {@code 400 (Bad Request)} if the etatFacture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatFacture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-factures")
    public ResponseEntity<EtatFacture> updateEtatFacture(@RequestBody EtatFacture etatFacture) throws URISyntaxException {
        log.debug("REST request to update EtatFacture : {}", etatFacture);
        if (etatFacture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatFacture result = etatFactureService.save(etatFacture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatFacture.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-factures} : get all the etatFactures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatFactures in body.
     */
    @GetMapping("/etat-factures")
    public ResponseEntity<List<EtatFacture>> getAllEtatFactures(Pageable pageable) {
        log.debug("REST request to get a page of EtatFactures");
        Page<EtatFacture> page = etatFactureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-factures/:id} : get the "id" etatFacture.
     *
     * @param id the id of the etatFacture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatFacture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-factures/{id}")
    public ResponseEntity<EtatFacture> getEtatFacture(@PathVariable Long id) {
        log.debug("REST request to get EtatFacture : {}", id);
        Optional<EtatFacture> etatFacture = etatFactureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatFacture);
    }

    /**
     * {@code DELETE  /etat-factures/:id} : delete the "id" etatFacture.
     *
     * @param id the id of the etatFacture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-factures/{id}")
    public ResponseEntity<Void> deleteEtatFacture(@PathVariable Long id) {
        log.debug("REST request to delete EtatFacture : {}", id);
        etatFactureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
