package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeProd;
import com.projet.hpd.service.TypeProdService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeProd}.
 */
@RestController
@RequestMapping("/api")
public class TypeProdResource {

    private final Logger log = LoggerFactory.getLogger(TypeProdResource.class);

    private static final String ENTITY_NAME = "typeProd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeProdService typeProdService;

    public TypeProdResource(TypeProdService typeProdService) {
        this.typeProdService = typeProdService;
    }

    /**
     * {@code POST  /type-prods} : Create a new typeProd.
     *
     * @param typeProd the typeProd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeProd, or with status {@code 400 (Bad Request)} if the typeProd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-prods")
    public ResponseEntity<TypeProd> createTypeProd(@RequestBody TypeProd typeProd) throws URISyntaxException {
        log.debug("REST request to save TypeProd : {}", typeProd);
        if (typeProd.getId() != null) {
            throw new BadRequestAlertException("A new typeProd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeProd result = typeProdService.save(typeProd);
        return ResponseEntity.created(new URI("/api/type-prods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-prods} : Updates an existing typeProd.
     *
     * @param typeProd the typeProd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeProd,
     * or with status {@code 400 (Bad Request)} if the typeProd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeProd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-prods")
    public ResponseEntity<TypeProd> updateTypeProd(@RequestBody TypeProd typeProd) throws URISyntaxException {
        log.debug("REST request to update TypeProd : {}", typeProd);
        if (typeProd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeProd result = typeProdService.save(typeProd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeProd.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-prods} : get all the typeProds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeProds in body.
     */
    @GetMapping("/type-prods")
    public ResponseEntity<List<TypeProd>> getAllTypeProds(Pageable pageable) {
        log.debug("REST request to get a page of TypeProds");
        Page<TypeProd> page = typeProdService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-prods/:id} : get the "id" typeProd.
     *
     * @param id the id of the typeProd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeProd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-prods/{id}")
    public ResponseEntity<TypeProd> getTypeProd(@PathVariable Long id) {
        log.debug("REST request to get TypeProd : {}", id);
        Optional<TypeProd> typeProd = typeProdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeProd);
    }

    /**
     * {@code DELETE  /type-prods/:id} : delete the "id" typeProd.
     *
     * @param id the id of the typeProd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-prods/{id}")
    public ResponseEntity<Void> deleteTypeProd(@PathVariable Long id) {
        log.debug("REST request to delete TypeProd : {}", id);
        typeProdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
