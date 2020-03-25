package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypePlanning;
import com.projet.hpd.service.TypePlanningService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypePlanning}.
 */
@RestController
@RequestMapping("/api")
public class TypePlanningResource {

    private final Logger log = LoggerFactory.getLogger(TypePlanningResource.class);

    private static final String ENTITY_NAME = "typePlanning";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePlanningService typePlanningService;

    public TypePlanningResource(TypePlanningService typePlanningService) {
        this.typePlanningService = typePlanningService;
    }

    /**
     * {@code POST  /type-plannings} : Create a new typePlanning.
     *
     * @param typePlanning the typePlanning to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePlanning, or with status {@code 400 (Bad Request)} if the typePlanning has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-plannings")
    public ResponseEntity<TypePlanning> createTypePlanning(@RequestBody TypePlanning typePlanning) throws URISyntaxException {
        log.debug("REST request to save TypePlanning : {}", typePlanning);
        if (typePlanning.getId() != null) {
            throw new BadRequestAlertException("A new typePlanning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePlanning result = typePlanningService.save(typePlanning);
        return ResponseEntity.created(new URI("/api/type-plannings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-plannings} : Updates an existing typePlanning.
     *
     * @param typePlanning the typePlanning to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePlanning,
     * or with status {@code 400 (Bad Request)} if the typePlanning is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePlanning couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-plannings")
    public ResponseEntity<TypePlanning> updateTypePlanning(@RequestBody TypePlanning typePlanning) throws URISyntaxException {
        log.debug("REST request to update TypePlanning : {}", typePlanning);
        if (typePlanning.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePlanning result = typePlanningService.save(typePlanning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePlanning.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-plannings} : get all the typePlannings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePlannings in body.
     */
    @GetMapping("/type-plannings")
    public ResponseEntity<List<TypePlanning>> getAllTypePlannings(Pageable pageable) {
        log.debug("REST request to get a page of TypePlannings");
        Page<TypePlanning> page = typePlanningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-plannings/:id} : get the "id" typePlanning.
     *
     * @param id the id of the typePlanning to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePlanning, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-plannings/{id}")
    public ResponseEntity<TypePlanning> getTypePlanning(@PathVariable Long id) {
        log.debug("REST request to get TypePlanning : {}", id);
        Optional<TypePlanning> typePlanning = typePlanningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePlanning);
    }

    /**
     * {@code DELETE  /type-plannings/:id} : delete the "id" typePlanning.
     *
     * @param id the id of the typePlanning to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-plannings/{id}")
    public ResponseEntity<Void> deleteTypePlanning(@PathVariable Long id) {
        log.debug("REST request to delete TypePlanning : {}", id);
        typePlanningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
