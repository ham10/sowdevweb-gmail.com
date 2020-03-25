package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeFacture;
import com.projet.hpd.service.TypeFactureService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeFacture}.
 */
@RestController
@RequestMapping("/api")
public class TypeFactureResource {

    private final Logger log = LoggerFactory.getLogger(TypeFactureResource.class);

    private static final String ENTITY_NAME = "typeFacture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeFactureService typeFactureService;

    public TypeFactureResource(TypeFactureService typeFactureService) {
        this.typeFactureService = typeFactureService;
    }

    /**
     * {@code POST  /type-factures} : Create a new typeFacture.
     *
     * @param typeFacture the typeFacture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeFacture, or with status {@code 400 (Bad Request)} if the typeFacture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-factures")
    public ResponseEntity<TypeFacture> createTypeFacture(@RequestBody TypeFacture typeFacture) throws URISyntaxException {
        log.debug("REST request to save TypeFacture : {}", typeFacture);
        if (typeFacture.getId() != null) {
            throw new BadRequestAlertException("A new typeFacture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeFacture result = typeFactureService.save(typeFacture);
        return ResponseEntity.created(new URI("/api/type-factures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-factures} : Updates an existing typeFacture.
     *
     * @param typeFacture the typeFacture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeFacture,
     * or with status {@code 400 (Bad Request)} if the typeFacture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeFacture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-factures")
    public ResponseEntity<TypeFacture> updateTypeFacture(@RequestBody TypeFacture typeFacture) throws URISyntaxException {
        log.debug("REST request to update TypeFacture : {}", typeFacture);
        if (typeFacture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeFacture result = typeFactureService.save(typeFacture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeFacture.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-factures} : get all the typeFactures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeFactures in body.
     */
    @GetMapping("/type-factures")
    public ResponseEntity<List<TypeFacture>> getAllTypeFactures(Pageable pageable) {
        log.debug("REST request to get a page of TypeFactures");
        Page<TypeFacture> page = typeFactureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-factures/:id} : get the "id" typeFacture.
     *
     * @param id the id of the typeFacture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeFacture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-factures/{id}")
    public ResponseEntity<TypeFacture> getTypeFacture(@PathVariable Long id) {
        log.debug("REST request to get TypeFacture : {}", id);
        Optional<TypeFacture> typeFacture = typeFactureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeFacture);
    }

    /**
     * {@code DELETE  /type-factures/:id} : delete the "id" typeFacture.
     *
     * @param id the id of the typeFacture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-factures/{id}")
    public ResponseEntity<Void> deleteTypeFacture(@PathVariable Long id) {
        log.debug("REST request to delete TypeFacture : {}", id);
        typeFactureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
