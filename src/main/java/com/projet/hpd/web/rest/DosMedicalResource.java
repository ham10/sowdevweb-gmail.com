package com.projet.hpd.web.rest;

import com.projet.hpd.domain.DosMedical;
import com.projet.hpd.service.DosMedicalService;
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
 * REST controller for managing {@link com.projet.hpd.domain.DosMedical}.
 */
@RestController
@RequestMapping("/api")
public class DosMedicalResource {

    private final Logger log = LoggerFactory.getLogger(DosMedicalResource.class);

    private static final String ENTITY_NAME = "dosMedical";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DosMedicalService dosMedicalService;

    public DosMedicalResource(DosMedicalService dosMedicalService) {
        this.dosMedicalService = dosMedicalService;
    }

    /**
     * {@code POST  /dos-medicals} : Create a new dosMedical.
     *
     * @param dosMedical the dosMedical to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dosMedical, or with status {@code 400 (Bad Request)} if the dosMedical has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dos-medicals")
    public ResponseEntity<DosMedical> createDosMedical(@RequestBody DosMedical dosMedical) throws URISyntaxException {
        log.debug("REST request to save DosMedical : {}", dosMedical);
        if (dosMedical.getId() != null) {
            throw new BadRequestAlertException("A new dosMedical cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DosMedical result = dosMedicalService.save(dosMedical);
        return ResponseEntity.created(new URI("/api/dos-medicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dos-medicals} : Updates an existing dosMedical.
     *
     * @param dosMedical the dosMedical to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dosMedical,
     * or with status {@code 400 (Bad Request)} if the dosMedical is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dosMedical couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dos-medicals")
    public ResponseEntity<DosMedical> updateDosMedical(@RequestBody DosMedical dosMedical) throws URISyntaxException {
        log.debug("REST request to update DosMedical : {}", dosMedical);
        if (dosMedical.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DosMedical result = dosMedicalService.save(dosMedical);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dosMedical.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dos-medicals} : get all the dosMedicals.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dosMedicals in body.
     */
    @GetMapping("/dos-medicals")
    public ResponseEntity<List<DosMedical>> getAllDosMedicals(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of DosMedicals");
        Page<DosMedical> page;
        if (eagerload) {
            page = dosMedicalService.findAllWithEagerRelationships(pageable);
        } else {
            page = dosMedicalService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dos-medicals/:id} : get the "id" dosMedical.
     *
     * @param id the id of the dosMedical to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dosMedical, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dos-medicals/{id}")
    public ResponseEntity<DosMedical> getDosMedical(@PathVariable Long id) {
        log.debug("REST request to get DosMedical : {}", id);
        Optional<DosMedical> dosMedical = dosMedicalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dosMedical);
    }

    /**
     * {@code DELETE  /dos-medicals/:id} : delete the "id" dosMedical.
     *
     * @param id the id of the dosMedical to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dos-medicals/{id}")
    public ResponseEntity<Void> deleteDosMedical(@PathVariable Long id) {
        log.debug("REST request to delete DosMedical : {}", id);
        dosMedicalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
