package com.projet.hpd.web.rest;

import com.projet.hpd.domain.SchemaCompta;
import com.projet.hpd.service.SchemaComptaService;
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
 * REST controller for managing {@link com.projet.hpd.domain.SchemaCompta}.
 */
@RestController
@RequestMapping("/api")
public class SchemaComptaResource {

    private final Logger log = LoggerFactory.getLogger(SchemaComptaResource.class);

    private static final String ENTITY_NAME = "schemaCompta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchemaComptaService schemaComptaService;

    public SchemaComptaResource(SchemaComptaService schemaComptaService) {
        this.schemaComptaService = schemaComptaService;
    }

    /**
     * {@code POST  /schema-comptas} : Create a new schemaCompta.
     *
     * @param schemaCompta the schemaCompta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schemaCompta, or with status {@code 400 (Bad Request)} if the schemaCompta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schema-comptas")
    public ResponseEntity<SchemaCompta> createSchemaCompta(@RequestBody SchemaCompta schemaCompta) throws URISyntaxException {
        log.debug("REST request to save SchemaCompta : {}", schemaCompta);
        if (schemaCompta.getId() != null) {
            throw new BadRequestAlertException("A new schemaCompta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchemaCompta result = schemaComptaService.save(schemaCompta);
        return ResponseEntity.created(new URI("/api/schema-comptas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schema-comptas} : Updates an existing schemaCompta.
     *
     * @param schemaCompta the schemaCompta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schemaCompta,
     * or with status {@code 400 (Bad Request)} if the schemaCompta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schemaCompta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schema-comptas")
    public ResponseEntity<SchemaCompta> updateSchemaCompta(@RequestBody SchemaCompta schemaCompta) throws URISyntaxException {
        log.debug("REST request to update SchemaCompta : {}", schemaCompta);
        if (schemaCompta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchemaCompta result = schemaComptaService.save(schemaCompta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schemaCompta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schema-comptas} : get all the schemaComptas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schemaComptas in body.
     */
    @GetMapping("/schema-comptas")
    public ResponseEntity<List<SchemaCompta>> getAllSchemaComptas(Pageable pageable) {
        log.debug("REST request to get a page of SchemaComptas");
        Page<SchemaCompta> page = schemaComptaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schema-comptas/:id} : get the "id" schemaCompta.
     *
     * @param id the id of the schemaCompta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schemaCompta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schema-comptas/{id}")
    public ResponseEntity<SchemaCompta> getSchemaCompta(@PathVariable Long id) {
        log.debug("REST request to get SchemaCompta : {}", id);
        Optional<SchemaCompta> schemaCompta = schemaComptaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schemaCompta);
    }

    /**
     * {@code DELETE  /schema-comptas/:id} : delete the "id" schemaCompta.
     *
     * @param id the id of the schemaCompta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schema-comptas/{id}")
    public ResponseEntity<Void> deleteSchemaCompta(@PathVariable Long id) {
        log.debug("REST request to delete SchemaCompta : {}", id);
        schemaComptaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
