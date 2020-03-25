package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Planning;
import com.projet.hpd.service.PlanningService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Planning}.
 */
@RestController
@RequestMapping("/api")
public class PlanningResource {

    private final Logger log = LoggerFactory.getLogger(PlanningResource.class);

    private static final String ENTITY_NAME = "planning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanningService planningService;

    public PlanningResource(PlanningService planningService) {
        this.planningService = planningService;
    }

    /**
     * {@code POST  /plannings} : Create a new planning.
     *
     * @param planning the planning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planning, or with status {@code 400 (Bad Request)} if the planning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plannings")
    public ResponseEntity<Planning> createPlanning(@RequestBody Planning planning) throws URISyntaxException {
        log.debug("REST request to save Planning : {}", planning);
        if (planning.getId() != null) {
            throw new BadRequestAlertException("A new planning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Planning result = planningService.save(planning);
        return ResponseEntity.created(new URI("/api/plannings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plannings} : Updates an existing planning.
     *
     * @param planning the planning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planning,
     * or with status {@code 400 (Bad Request)} if the planning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plannings")
    public ResponseEntity<Planning> updatePlanning(@RequestBody Planning planning) throws URISyntaxException {
        log.debug("REST request to update Planning : {}", planning);
        if (planning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Planning result = planningService.save(planning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planning.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plannings} : get all the plannings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plannings in body.
     */
    @GetMapping("/plannings")
    public ResponseEntity<List<Planning>> getAllPlannings(Pageable pageable) {
        log.debug("REST request to get a page of Plannings");
        Page<Planning> page = planningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plannings/:id} : get the "id" planning.
     *
     * @param id the id of the planning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plannings/{id}")
    public ResponseEntity<Planning> getPlanning(@PathVariable Long id) {
        log.debug("REST request to get Planning : {}", id);
        Optional<Planning> planning = planningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planning);
    }

    /**
     * {@code DELETE  /plannings/:id} : delete the "id" planning.
     *
     * @param id the id of the planning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plannings/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id) {
        log.debug("REST request to delete Planning : {}", id);
        planningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
