package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeLit;
import com.projet.hpd.service.TypeLitService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeLit}.
 */
@RestController
@RequestMapping("/api")
public class TypeLitResource {

    private final Logger log = LoggerFactory.getLogger(TypeLitResource.class);

    private static final String ENTITY_NAME = "typeLit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeLitService typeLitService;

    public TypeLitResource(TypeLitService typeLitService) {
        this.typeLitService = typeLitService;
    }

    /**
     * {@code POST  /type-lits} : Create a new typeLit.
     *
     * @param typeLit the typeLit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeLit, or with status {@code 400 (Bad Request)} if the typeLit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-lits")
    public ResponseEntity<TypeLit> createTypeLit(@RequestBody TypeLit typeLit) throws URISyntaxException {
        log.debug("REST request to save TypeLit : {}", typeLit);
        if (typeLit.getId() != null) {
            throw new BadRequestAlertException("A new typeLit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeLit result = typeLitService.save(typeLit);
        return ResponseEntity.created(new URI("/api/type-lits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-lits} : Updates an existing typeLit.
     *
     * @param typeLit the typeLit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeLit,
     * or with status {@code 400 (Bad Request)} if the typeLit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeLit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-lits")
    public ResponseEntity<TypeLit> updateTypeLit(@RequestBody TypeLit typeLit) throws URISyntaxException {
        log.debug("REST request to update TypeLit : {}", typeLit);
        if (typeLit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeLit result = typeLitService.save(typeLit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeLit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-lits} : get all the typeLits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeLits in body.
     */
    @GetMapping("/type-lits")
    public ResponseEntity<List<TypeLit>> getAllTypeLits(Pageable pageable) {
        log.debug("REST request to get a page of TypeLits");
        Page<TypeLit> page = typeLitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-lits/:id} : get the "id" typeLit.
     *
     * @param id the id of the typeLit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeLit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-lits/{id}")
    public ResponseEntity<TypeLit> getTypeLit(@PathVariable Long id) {
        log.debug("REST request to get TypeLit : {}", id);
        Optional<TypeLit> typeLit = typeLitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeLit);
    }

    /**
     * {@code DELETE  /type-lits/:id} : delete the "id" typeLit.
     *
     * @param id the id of the typeLit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-lits/{id}")
    public ResponseEntity<Void> deleteTypeLit(@PathVariable Long id) {
        log.debug("REST request to delete TypeLit : {}", id);
        typeLitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
