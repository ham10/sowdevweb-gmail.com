package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeFact;
import com.projet.hpd.service.TypeFactService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeFact}.
 */
@RestController
@RequestMapping("/api")
public class TypeFactResource {

    private final Logger log = LoggerFactory.getLogger(TypeFactResource.class);

    private static final String ENTITY_NAME = "typeFact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeFactService typeFactService;

    public TypeFactResource(TypeFactService typeFactService) {
        this.typeFactService = typeFactService;
    }

    /**
     * {@code POST  /type-facts} : Create a new typeFact.
     *
     * @param typeFact the typeFact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeFact, or with status {@code 400 (Bad Request)} if the typeFact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-facts")
    public ResponseEntity<TypeFact> createTypeFact(@RequestBody TypeFact typeFact) throws URISyntaxException {
        log.debug("REST request to save TypeFact : {}", typeFact);
        if (typeFact.getId() != null) {
            throw new BadRequestAlertException("A new typeFact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeFact result = typeFactService.save(typeFact);
        return ResponseEntity.created(new URI("/api/type-facts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-facts} : Updates an existing typeFact.
     *
     * @param typeFact the typeFact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeFact,
     * or with status {@code 400 (Bad Request)} if the typeFact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeFact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-facts")
    public ResponseEntity<TypeFact> updateTypeFact(@RequestBody TypeFact typeFact) throws URISyntaxException {
        log.debug("REST request to update TypeFact : {}", typeFact);
        if (typeFact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeFact result = typeFactService.save(typeFact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeFact.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-facts} : get all the typeFacts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeFacts in body.
     */
    @GetMapping("/type-facts")
    public ResponseEntity<List<TypeFact>> getAllTypeFacts(Pageable pageable) {
        log.debug("REST request to get a page of TypeFacts");
        Page<TypeFact> page = typeFactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-facts/:id} : get the "id" typeFact.
     *
     * @param id the id of the typeFact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeFact, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-facts/{id}")
    public ResponseEntity<TypeFact> getTypeFact(@PathVariable Long id) {
        log.debug("REST request to get TypeFact : {}", id);
        Optional<TypeFact> typeFact = typeFactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeFact);
    }

    /**
     * {@code DELETE  /type-facts/:id} : delete the "id" typeFact.
     *
     * @param id the id of the typeFact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-facts/{id}")
    public ResponseEntity<Void> deleteTypeFact(@PathVariable Long id) {
        log.debug("REST request to delete TypeFact : {}", id);
        typeFactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
