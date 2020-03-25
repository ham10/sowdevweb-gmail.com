package com.projet.hpd.web.rest;

import com.projet.hpd.domain.EtatImmo;
import com.projet.hpd.service.EtatImmoService;
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
 * REST controller for managing {@link com.projet.hpd.domain.EtatImmo}.
 */
@RestController
@RequestMapping("/api")
public class EtatImmoResource {

    private final Logger log = LoggerFactory.getLogger(EtatImmoResource.class);

    private static final String ENTITY_NAME = "etatImmo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatImmoService etatImmoService;

    public EtatImmoResource(EtatImmoService etatImmoService) {
        this.etatImmoService = etatImmoService;
    }

    /**
     * {@code POST  /etat-immos} : Create a new etatImmo.
     *
     * @param etatImmo the etatImmo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatImmo, or with status {@code 400 (Bad Request)} if the etatImmo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-immos")
    public ResponseEntity<EtatImmo> createEtatImmo(@RequestBody EtatImmo etatImmo) throws URISyntaxException {
        log.debug("REST request to save EtatImmo : {}", etatImmo);
        if (etatImmo.getId() != null) {
            throw new BadRequestAlertException("A new etatImmo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatImmo result = etatImmoService.save(etatImmo);
        return ResponseEntity.created(new URI("/api/etat-immos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-immos} : Updates an existing etatImmo.
     *
     * @param etatImmo the etatImmo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatImmo,
     * or with status {@code 400 (Bad Request)} if the etatImmo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatImmo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-immos")
    public ResponseEntity<EtatImmo> updateEtatImmo(@RequestBody EtatImmo etatImmo) throws URISyntaxException {
        log.debug("REST request to update EtatImmo : {}", etatImmo);
        if (etatImmo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatImmo result = etatImmoService.save(etatImmo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatImmo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-immos} : get all the etatImmos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatImmos in body.
     */
    @GetMapping("/etat-immos")
    public ResponseEntity<List<EtatImmo>> getAllEtatImmos(Pageable pageable) {
        log.debug("REST request to get a page of EtatImmos");
        Page<EtatImmo> page = etatImmoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-immos/:id} : get the "id" etatImmo.
     *
     * @param id the id of the etatImmo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatImmo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-immos/{id}")
    public ResponseEntity<EtatImmo> getEtatImmo(@PathVariable Long id) {
        log.debug("REST request to get EtatImmo : {}", id);
        Optional<EtatImmo> etatImmo = etatImmoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatImmo);
    }

    /**
     * {@code DELETE  /etat-immos/:id} : delete the "id" etatImmo.
     *
     * @param id the id of the etatImmo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-immos/{id}")
    public ResponseEntity<Void> deleteEtatImmo(@PathVariable Long id) {
        log.debug("REST request to delete EtatImmo : {}", id);
        etatImmoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
