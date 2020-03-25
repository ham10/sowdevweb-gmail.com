package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeDoc;
import com.projet.hpd.service.TypeDocService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeDoc}.
 */
@RestController
@RequestMapping("/api")
public class TypeDocResource {

    private final Logger log = LoggerFactory.getLogger(TypeDocResource.class);

    private static final String ENTITY_NAME = "typeDoc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeDocService typeDocService;

    public TypeDocResource(TypeDocService typeDocService) {
        this.typeDocService = typeDocService;
    }

    /**
     * {@code POST  /type-docs} : Create a new typeDoc.
     *
     * @param typeDoc the typeDoc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeDoc, or with status {@code 400 (Bad Request)} if the typeDoc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-docs")
    public ResponseEntity<TypeDoc> createTypeDoc(@RequestBody TypeDoc typeDoc) throws URISyntaxException {
        log.debug("REST request to save TypeDoc : {}", typeDoc);
        if (typeDoc.getId() != null) {
            throw new BadRequestAlertException("A new typeDoc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDoc result = typeDocService.save(typeDoc);
        return ResponseEntity.created(new URI("/api/type-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-docs} : Updates an existing typeDoc.
     *
     * @param typeDoc the typeDoc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDoc,
     * or with status {@code 400 (Bad Request)} if the typeDoc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeDoc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-docs")
    public ResponseEntity<TypeDoc> updateTypeDoc(@RequestBody TypeDoc typeDoc) throws URISyntaxException {
        log.debug("REST request to update TypeDoc : {}", typeDoc);
        if (typeDoc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDoc result = typeDocService.save(typeDoc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeDoc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-docs} : get all the typeDocs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeDocs in body.
     */
    @GetMapping("/type-docs")
    public ResponseEntity<List<TypeDoc>> getAllTypeDocs(Pageable pageable) {
        log.debug("REST request to get a page of TypeDocs");
        Page<TypeDoc> page = typeDocService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-docs/:id} : get the "id" typeDoc.
     *
     * @param id the id of the typeDoc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeDoc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-docs/{id}")
    public ResponseEntity<TypeDoc> getTypeDoc(@PathVariable Long id) {
        log.debug("REST request to get TypeDoc : {}", id);
        Optional<TypeDoc> typeDoc = typeDocService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDoc);
    }

    /**
     * {@code DELETE  /type-docs/:id} : delete the "id" typeDoc.
     *
     * @param id the id of the typeDoc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-docs/{id}")
    public ResponseEntity<Void> deleteTypeDoc(@PathVariable Long id) {
        log.debug("REST request to delete TypeDoc : {}", id);
        typeDocService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
