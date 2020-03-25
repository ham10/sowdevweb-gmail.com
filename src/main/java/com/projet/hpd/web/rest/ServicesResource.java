package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Services;
import com.projet.hpd.service.ServicesService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Services}.
 */
@RestController
@RequestMapping("/api")
public class ServicesResource {

    private final Logger log = LoggerFactory.getLogger(ServicesResource.class);

    private static final String ENTITY_NAME = "services";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicesService servicesService;

    public ServicesResource(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    /**
     * {@code POST  /services} : Create a new services.
     *
     * @param services the services to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new services, or with status {@code 400 (Bad Request)} if the services has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/services")
    public ResponseEntity<Services> createServices(@RequestBody Services services) throws URISyntaxException {
        log.debug("REST request to save Services : {}", services);
        if (services.getId() != null) {
            throw new BadRequestAlertException("A new services cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Services result = servicesService.save(services);
        return ResponseEntity.created(new URI("/api/services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /services} : Updates an existing services.
     *
     * @param services the services to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated services,
     * or with status {@code 400 (Bad Request)} if the services is not valid,
     * or with status {@code 500 (Internal Server Error)} if the services couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/services")
    public ResponseEntity<Services> updateServices(@RequestBody Services services) throws URISyntaxException {
        log.debug("REST request to update Services : {}", services);
        if (services.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Services result = servicesService.save(services);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, services.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /services} : get all the services.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of services in body.
     */
    @GetMapping("/services")
    public ResponseEntity<List<Services>> getAllServices(Pageable pageable) {
        log.debug("REST request to get a page of Services");
        Page<Services> page = servicesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /services/:id} : get the "id" services.
     *
     * @param id the id of the services to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the services, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/services/{id}")
    public ResponseEntity<Services> getServices(@PathVariable Long id) {
        log.debug("REST request to get Services : {}", id);
        Optional<Services> services = servicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(services);
    }

    /**
     * {@code DELETE  /services/:id} : delete the "id" services.
     *
     * @param id the id of the services to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteServices(@PathVariable Long id) {
        log.debug("REST request to delete Services : {}", id);
        servicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
