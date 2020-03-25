package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeImmo;
import com.projet.hpd.service.TypeImmoService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeImmo}.
 */
@RestController
@RequestMapping("/api")
public class TypeImmoResource {

    private final Logger log = LoggerFactory.getLogger(TypeImmoResource.class);

    private static final String ENTITY_NAME = "typeImmo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeImmoService typeImmoService;

    public TypeImmoResource(TypeImmoService typeImmoService) {
        this.typeImmoService = typeImmoService;
    }

    /**
     * {@code POST  /type-immos} : Create a new typeImmo.
     *
     * @param typeImmo the typeImmo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeImmo, or with status {@code 400 (Bad Request)} if the typeImmo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-immos")
    public ResponseEntity<TypeImmo> createTypeImmo(@RequestBody TypeImmo typeImmo) throws URISyntaxException {
        log.debug("REST request to save TypeImmo : {}", typeImmo);
        if (typeImmo.getId() != null) {
            throw new BadRequestAlertException("A new typeImmo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeImmo result = typeImmoService.save(typeImmo);
        return ResponseEntity.created(new URI("/api/type-immos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-immos} : Updates an existing typeImmo.
     *
     * @param typeImmo the typeImmo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeImmo,
     * or with status {@code 400 (Bad Request)} if the typeImmo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeImmo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-immos")
    public ResponseEntity<TypeImmo> updateTypeImmo(@RequestBody TypeImmo typeImmo) throws URISyntaxException {
        log.debug("REST request to update TypeImmo : {}", typeImmo);
        if (typeImmo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeImmo result = typeImmoService.save(typeImmo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeImmo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-immos} : get all the typeImmos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeImmos in body.
     */
    @GetMapping("/type-immos")
    public ResponseEntity<List<TypeImmo>> getAllTypeImmos(Pageable pageable) {
        log.debug("REST request to get a page of TypeImmos");
        Page<TypeImmo> page = typeImmoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-immos/:id} : get the "id" typeImmo.
     *
     * @param id the id of the typeImmo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeImmo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-immos/{id}")
    public ResponseEntity<TypeImmo> getTypeImmo(@PathVariable Long id) {
        log.debug("REST request to get TypeImmo : {}", id);
        Optional<TypeImmo> typeImmo = typeImmoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeImmo);
    }

    /**
     * {@code DELETE  /type-immos/:id} : delete the "id" typeImmo.
     *
     * @param id the id of the typeImmo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-immos/{id}")
    public ResponseEntity<Void> deleteTypeImmo(@PathVariable Long id) {
        log.debug("REST request to delete TypeImmo : {}", id);
        typeImmoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
