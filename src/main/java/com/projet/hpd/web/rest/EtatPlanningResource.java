package com.projet.hpd.web.rest;

import com.projet.hpd.domain.EtatPlanning;
import com.projet.hpd.service.EtatPlanningService;
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
 * REST controller for managing {@link com.projet.hpd.domain.EtatPlanning}.
 */
@RestController
@RequestMapping("/api")
public class EtatPlanningResource {

    private final Logger log = LoggerFactory.getLogger(EtatPlanningResource.class);

    private static final String ENTITY_NAME = "etatPlanning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatPlanningService etatPlanningService;

    public EtatPlanningResource(EtatPlanningService etatPlanningService) {
        this.etatPlanningService = etatPlanningService;
    }

    /**
     * {@code POST  /etat-plannings} : Create a new etatPlanning.
     *
     * @param etatPlanning the etatPlanning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatPlanning, or with status {@code 400 (Bad Request)} if the etatPlanning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-plannings")
    public ResponseEntity<EtatPlanning> createEtatPlanning(@RequestBody EtatPlanning etatPlanning) throws URISyntaxException {
        log.debug("REST request to save EtatPlanning : {}", etatPlanning);
        if (etatPlanning.getId() != null) {
            throw new BadRequestAlertException("A new etatPlanning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatPlanning result = etatPlanningService.save(etatPlanning);
        return ResponseEntity.created(new URI("/api/etat-plannings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-plannings} : Updates an existing etatPlanning.
     *
     * @param etatPlanning the etatPlanning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatPlanning,
     * or with status {@code 400 (Bad Request)} if the etatPlanning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatPlanning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-plannings")
    public ResponseEntity<EtatPlanning> updateEtatPlanning(@RequestBody EtatPlanning etatPlanning) throws URISyntaxException {
        log.debug("REST request to update EtatPlanning : {}", etatPlanning);
        if (etatPlanning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatPlanning result = etatPlanningService.save(etatPlanning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatPlanning.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-plannings} : get all the etatPlannings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatPlannings in body.
     */
    @GetMapping("/etat-plannings")
    public ResponseEntity<List<EtatPlanning>> getAllEtatPlannings(Pageable pageable) {
        log.debug("REST request to get a page of EtatPlannings");
        Page<EtatPlanning> page = etatPlanningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-plannings/:id} : get the "id" etatPlanning.
     *
     * @param id the id of the etatPlanning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatPlanning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-plannings/{id}")
    public ResponseEntity<EtatPlanning> getEtatPlanning(@PathVariable Long id) {
        log.debug("REST request to get EtatPlanning : {}", id);
        Optional<EtatPlanning> etatPlanning = etatPlanningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatPlanning);
    }

    /**
     * {@code DELETE  /etat-plannings/:id} : delete the "id" etatPlanning.
     *
     * @param id the id of the etatPlanning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-plannings/{id}")
    public ResponseEntity<Void> deleteEtatPlanning(@PathVariable Long id) {
        log.debug("REST request to delete EtatPlanning : {}", id);
        etatPlanningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
