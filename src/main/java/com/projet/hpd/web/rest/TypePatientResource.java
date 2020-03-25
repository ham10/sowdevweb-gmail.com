package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypePatient;
import com.projet.hpd.service.TypePatientService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypePatient}.
 */
@RestController
@RequestMapping("/api")
public class TypePatientResource {

    private final Logger log = LoggerFactory.getLogger(TypePatientResource.class);

    private static final String ENTITY_NAME = "typePatient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePatientService typePatientService;

    public TypePatientResource(TypePatientService typePatientService) {
        this.typePatientService = typePatientService;
    }

    /**
     * {@code POST  /type-patients} : Create a new typePatient.
     *
     * @param typePatient the typePatient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePatient, or with status {@code 400 (Bad Request)} if the typePatient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-patients")
    public ResponseEntity<TypePatient> createTypePatient(@RequestBody TypePatient typePatient) throws URISyntaxException {
        log.debug("REST request to save TypePatient : {}", typePatient);
        if (typePatient.getId() != null) {
            throw new BadRequestAlertException("A new typePatient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePatient result = typePatientService.save(typePatient);
        return ResponseEntity.created(new URI("/api/type-patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-patients} : Updates an existing typePatient.
     *
     * @param typePatient the typePatient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePatient,
     * or with status {@code 400 (Bad Request)} if the typePatient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePatient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-patients")
    public ResponseEntity<TypePatient> updateTypePatient(@RequestBody TypePatient typePatient) throws URISyntaxException {
        log.debug("REST request to update TypePatient : {}", typePatient);
        if (typePatient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePatient result = typePatientService.save(typePatient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePatient.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-patients} : get all the typePatients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePatients in body.
     */
    @GetMapping("/type-patients")
    public ResponseEntity<List<TypePatient>> getAllTypePatients(Pageable pageable) {
        log.debug("REST request to get a page of TypePatients");
        Page<TypePatient> page = typePatientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-patients/:id} : get the "id" typePatient.
     *
     * @param id the id of the typePatient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePatient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-patients/{id}")
    public ResponseEntity<TypePatient> getTypePatient(@PathVariable Long id) {
        log.debug("REST request to get TypePatient : {}", id);
        Optional<TypePatient> typePatient = typePatientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePatient);
    }

    /**
     * {@code DELETE  /type-patients/:id} : delete the "id" typePatient.
     *
     * @param id the id of the typePatient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-patients/{id}")
    public ResponseEntity<Void> deleteTypePatient(@PathVariable Long id) {
        log.debug("REST request to delete TypePatient : {}", id);
        typePatientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
