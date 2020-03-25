package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Ecriture;
import com.projet.hpd.service.EcritureService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Ecriture}.
 */
@RestController
@RequestMapping("/api")
public class EcritureResource {

    private final Logger log = LoggerFactory.getLogger(EcritureResource.class);

    private static final String ENTITY_NAME = "ecriture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcritureService ecritureService;

    public EcritureResource(EcritureService ecritureService) {
        this.ecritureService = ecritureService;
    }

    /**
     * {@code POST  /ecritures} : Create a new ecriture.
     *
     * @param ecriture the ecriture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecriture, or with status {@code 400 (Bad Request)} if the ecriture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecritures")
    public ResponseEntity<Ecriture> createEcriture(@RequestBody Ecriture ecriture) throws URISyntaxException {
        log.debug("REST request to save Ecriture : {}", ecriture);
        if (ecriture.getId() != null) {
            throw new BadRequestAlertException("A new ecriture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ecriture result = ecritureService.save(ecriture);
        return ResponseEntity.created(new URI("/api/ecritures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ecritures} : Updates an existing ecriture.
     *
     * @param ecriture the ecriture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecriture,
     * or with status {@code 400 (Bad Request)} if the ecriture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecriture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ecritures")
    public ResponseEntity<Ecriture> updateEcriture(@RequestBody Ecriture ecriture) throws URISyntaxException {
        log.debug("REST request to update Ecriture : {}", ecriture);
        if (ecriture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ecriture result = ecritureService.save(ecriture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecriture.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ecritures} : get all the ecritures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecritures in body.
     */
    @GetMapping("/ecritures")
    public ResponseEntity<List<Ecriture>> getAllEcritures(Pageable pageable) {
        log.debug("REST request to get a page of Ecritures");
        Page<Ecriture> page = ecritureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ecritures/:id} : get the "id" ecriture.
     *
     * @param id the id of the ecriture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecriture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ecritures/{id}")
    public ResponseEntity<Ecriture> getEcriture(@PathVariable Long id) {
        log.debug("REST request to get Ecriture : {}", id);
        Optional<Ecriture> ecriture = ecritureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecriture);
    }

    /**
     * {@code DELETE  /ecritures/:id} : delete the "id" ecriture.
     *
     * @param id the id of the ecriture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ecritures/{id}")
    public ResponseEntity<Void> deleteEcriture(@PathVariable Long id) {
        log.debug("REST request to delete Ecriture : {}", id);
        ecritureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
