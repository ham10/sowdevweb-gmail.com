package com.projet.hpd.web.rest;

import com.projet.hpd.domain.MachAutorise;
import com.projet.hpd.service.MachAutoriseService;
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
 * REST controller for managing {@link com.projet.hpd.domain.MachAutorise}.
 */
@RestController
@RequestMapping("/api")
public class MachAutoriseResource {

    private final Logger log = LoggerFactory.getLogger(MachAutoriseResource.class);

    private static final String ENTITY_NAME = "machAutorise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MachAutoriseService machAutoriseService;

    public MachAutoriseResource(MachAutoriseService machAutoriseService) {
        this.machAutoriseService = machAutoriseService;
    }

    /**
     * {@code POST  /mach-autorises} : Create a new machAutorise.
     *
     * @param machAutorise the machAutorise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new machAutorise, or with status {@code 400 (Bad Request)} if the machAutorise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mach-autorises")
    public ResponseEntity<MachAutorise> createMachAutorise(@RequestBody MachAutorise machAutorise) throws URISyntaxException {
        log.debug("REST request to save MachAutorise : {}", machAutorise);
        if (machAutorise.getId() != null) {
            throw new BadRequestAlertException("A new machAutorise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MachAutorise result = machAutoriseService.save(machAutorise);
        return ResponseEntity.created(new URI("/api/mach-autorises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mach-autorises} : Updates an existing machAutorise.
     *
     * @param machAutorise the machAutorise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machAutorise,
     * or with status {@code 400 (Bad Request)} if the machAutorise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the machAutorise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mach-autorises")
    public ResponseEntity<MachAutorise> updateMachAutorise(@RequestBody MachAutorise machAutorise) throws URISyntaxException {
        log.debug("REST request to update MachAutorise : {}", machAutorise);
        if (machAutorise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MachAutorise result = machAutoriseService.save(machAutorise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machAutorise.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mach-autorises} : get all the machAutorises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of machAutorises in body.
     */
    @GetMapping("/mach-autorises")
    public ResponseEntity<List<MachAutorise>> getAllMachAutorises(Pageable pageable) {
        log.debug("REST request to get a page of MachAutorises");
        Page<MachAutorise> page = machAutoriseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mach-autorises/:id} : get the "id" machAutorise.
     *
     * @param id the id of the machAutorise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the machAutorise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mach-autorises/{id}")
    public ResponseEntity<MachAutorise> getMachAutorise(@PathVariable Long id) {
        log.debug("REST request to get MachAutorise : {}", id);
        Optional<MachAutorise> machAutorise = machAutoriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(machAutorise);
    }

    /**
     * {@code DELETE  /mach-autorises/:id} : delete the "id" machAutorise.
     *
     * @param id the id of the machAutorise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mach-autorises/{id}")
    public ResponseEntity<Void> deleteMachAutorise(@PathVariable Long id) {
        log.debug("REST request to delete MachAutorise : {}", id);
        machAutoriseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
