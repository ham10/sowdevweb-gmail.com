package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeServices;
import com.projet.hpd.service.TypeServicesService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeServices}.
 */
@RestController
@RequestMapping("/api")
public class TypeServicesResource {

    private final Logger log = LoggerFactory.getLogger(TypeServicesResource.class);

    private static final String ENTITY_NAME = "typeServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeServicesService typeServicesService;

    public TypeServicesResource(TypeServicesService typeServicesService) {
        this.typeServicesService = typeServicesService;
    }

    /**
     * {@code POST  /type-services} : Create a new typeServices.
     *
     * @param typeServices the typeServices to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeServices, or with status {@code 400 (Bad Request)} if the typeServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-services")
    public ResponseEntity<TypeServices> createTypeServices(@RequestBody TypeServices typeServices) throws URISyntaxException {
        log.debug("REST request to save TypeServices : {}", typeServices);
        if (typeServices.getId() != null) {
            throw new BadRequestAlertException("A new typeServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeServices result = typeServicesService.save(typeServices);
        return ResponseEntity.created(new URI("/api/type-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-services} : Updates an existing typeServices.
     *
     * @param typeServices the typeServices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeServices,
     * or with status {@code 400 (Bad Request)} if the typeServices is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeServices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-services")
    public ResponseEntity<TypeServices> updateTypeServices(@RequestBody TypeServices typeServices) throws URISyntaxException {
        log.debug("REST request to update TypeServices : {}", typeServices);
        if (typeServices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeServices result = typeServicesService.save(typeServices);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeServices.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-services} : get all the typeServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeServices in body.
     */
    @GetMapping("/type-services")
    public ResponseEntity<List<TypeServices>> getAllTypeServices(Pageable pageable) {
        log.debug("REST request to get a page of TypeServices");
        Page<TypeServices> page = typeServicesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-services/:id} : get the "id" typeServices.
     *
     * @param id the id of the typeServices to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeServices, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-services/{id}")
    public ResponseEntity<TypeServices> getTypeServices(@PathVariable Long id) {
        log.debug("REST request to get TypeServices : {}", id);
        Optional<TypeServices> typeServices = typeServicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeServices);
    }

    /**
     * {@code DELETE  /type-services/:id} : delete the "id" typeServices.
     *
     * @param id the id of the typeServices to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-services/{id}")
    public ResponseEntity<Void> deleteTypeServices(@PathVariable Long id) {
        log.debug("REST request to delete TypeServices : {}", id);
        typeServicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
