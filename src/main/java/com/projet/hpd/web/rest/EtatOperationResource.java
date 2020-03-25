package com.projet.hpd.web.rest;

import com.projet.hpd.domain.EtatOperation;
import com.projet.hpd.service.EtatOperationService;
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
 * REST controller for managing {@link com.projet.hpd.domain.EtatOperation}.
 */
@RestController
@RequestMapping("/api")
public class EtatOperationResource {

    private final Logger log = LoggerFactory.getLogger(EtatOperationResource.class);

    private static final String ENTITY_NAME = "etatOperation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatOperationService etatOperationService;

    public EtatOperationResource(EtatOperationService etatOperationService) {
        this.etatOperationService = etatOperationService;
    }

    /**
     * {@code POST  /etat-operations} : Create a new etatOperation.
     *
     * @param etatOperation the etatOperation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatOperation, or with status {@code 400 (Bad Request)} if the etatOperation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-operations")
    public ResponseEntity<EtatOperation> createEtatOperation(@RequestBody EtatOperation etatOperation) throws URISyntaxException {
        log.debug("REST request to save EtatOperation : {}", etatOperation);
        if (etatOperation.getId() != null) {
            throw new BadRequestAlertException("A new etatOperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatOperation result = etatOperationService.save(etatOperation);
        return ResponseEntity.created(new URI("/api/etat-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-operations} : Updates an existing etatOperation.
     *
     * @param etatOperation the etatOperation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatOperation,
     * or with status {@code 400 (Bad Request)} if the etatOperation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatOperation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-operations")
    public ResponseEntity<EtatOperation> updateEtatOperation(@RequestBody EtatOperation etatOperation) throws URISyntaxException {
        log.debug("REST request to update EtatOperation : {}", etatOperation);
        if (etatOperation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatOperation result = etatOperationService.save(etatOperation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatOperation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-operations} : get all the etatOperations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatOperations in body.
     */
    @GetMapping("/etat-operations")
    public ResponseEntity<List<EtatOperation>> getAllEtatOperations(Pageable pageable) {
        log.debug("REST request to get a page of EtatOperations");
        Page<EtatOperation> page = etatOperationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-operations/:id} : get the "id" etatOperation.
     *
     * @param id the id of the etatOperation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatOperation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-operations/{id}")
    public ResponseEntity<EtatOperation> getEtatOperation(@PathVariable Long id) {
        log.debug("REST request to get EtatOperation : {}", id);
        Optional<EtatOperation> etatOperation = etatOperationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatOperation);
    }

    /**
     * {@code DELETE  /etat-operations/:id} : delete the "id" etatOperation.
     *
     * @param id the id of the etatOperation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-operations/{id}")
    public ResponseEntity<Void> deleteEtatOperation(@PathVariable Long id) {
        log.debug("REST request to delete EtatOperation : {}", id);
        etatOperationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
