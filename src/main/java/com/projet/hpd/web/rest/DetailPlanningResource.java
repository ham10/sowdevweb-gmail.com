package com.projet.hpd.web.rest;

import com.projet.hpd.domain.DetailPlanning;
import com.projet.hpd.service.DetailPlanningService;
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
 * REST controller for managing {@link com.projet.hpd.domain.DetailPlanning}.
 */
@RestController
@RequestMapping("/api")
public class DetailPlanningResource {

    private final Logger log = LoggerFactory.getLogger(DetailPlanningResource.class);

    private static final String ENTITY_NAME = "detailPlanning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailPlanningService detailPlanningService;

    public DetailPlanningResource(DetailPlanningService detailPlanningService) {
        this.detailPlanningService = detailPlanningService;
    }

    /**
     * {@code POST  /detail-plannings} : Create a new detailPlanning.
     *
     * @param detailPlanning the detailPlanning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailPlanning, or with status {@code 400 (Bad Request)} if the detailPlanning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-plannings")
    public ResponseEntity<DetailPlanning> createDetailPlanning(@RequestBody DetailPlanning detailPlanning) throws URISyntaxException {
        log.debug("REST request to save DetailPlanning : {}", detailPlanning);
        if (detailPlanning.getId() != null) {
            throw new BadRequestAlertException("A new detailPlanning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailPlanning result = detailPlanningService.save(detailPlanning);
        return ResponseEntity.created(new URI("/api/detail-plannings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-plannings} : Updates an existing detailPlanning.
     *
     * @param detailPlanning the detailPlanning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailPlanning,
     * or with status {@code 400 (Bad Request)} if the detailPlanning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailPlanning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-plannings")
    public ResponseEntity<DetailPlanning> updateDetailPlanning(@RequestBody DetailPlanning detailPlanning) throws URISyntaxException {
        log.debug("REST request to update DetailPlanning : {}", detailPlanning);
        if (detailPlanning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailPlanning result = detailPlanningService.save(detailPlanning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailPlanning.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detail-plannings} : get all the detailPlannings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailPlannings in body.
     */
    @GetMapping("/detail-plannings")
    public ResponseEntity<List<DetailPlanning>> getAllDetailPlannings(Pageable pageable) {
        log.debug("REST request to get a page of DetailPlannings");
        Page<DetailPlanning> page = detailPlanningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detail-plannings/:id} : get the "id" detailPlanning.
     *
     * @param id the id of the detailPlanning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailPlanning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-plannings/{id}")
    public ResponseEntity<DetailPlanning> getDetailPlanning(@PathVariable Long id) {
        log.debug("REST request to get DetailPlanning : {}", id);
        Optional<DetailPlanning> detailPlanning = detailPlanningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailPlanning);
    }

    /**
     * {@code DELETE  /detail-plannings/:id} : delete the "id" detailPlanning.
     *
     * @param id the id of the detailPlanning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-plannings/{id}")
    public ResponseEntity<Void> deleteDetailPlanning(@PathVariable Long id) {
        log.debug("REST request to delete DetailPlanning : {}", id);
        detailPlanningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
