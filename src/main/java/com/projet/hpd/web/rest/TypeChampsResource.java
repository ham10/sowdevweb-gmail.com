package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeChamps;
import com.projet.hpd.service.TypeChampsService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeChamps}.
 */
@RestController
@RequestMapping("/api")
public class TypeChampsResource {

    private final Logger log = LoggerFactory.getLogger(TypeChampsResource.class);

    private static final String ENTITY_NAME = "typeChamps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeChampsService typeChampsService;

    public TypeChampsResource(TypeChampsService typeChampsService) {
        this.typeChampsService = typeChampsService;
    }

    /**
     * {@code POST  /type-champs} : Create a new typeChamps.
     *
     * @param typeChamps the typeChamps to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeChamps, or with status {@code 400 (Bad Request)} if the typeChamps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-champs")
    public ResponseEntity<TypeChamps> createTypeChamps(@RequestBody TypeChamps typeChamps) throws URISyntaxException {
        log.debug("REST request to save TypeChamps : {}", typeChamps);
        if (typeChamps.getId() != null) {
            throw new BadRequestAlertException("A new typeChamps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeChamps result = typeChampsService.save(typeChamps);
        return ResponseEntity.created(new URI("/api/type-champs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-champs} : Updates an existing typeChamps.
     *
     * @param typeChamps the typeChamps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeChamps,
     * or with status {@code 400 (Bad Request)} if the typeChamps is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeChamps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-champs")
    public ResponseEntity<TypeChamps> updateTypeChamps(@RequestBody TypeChamps typeChamps) throws URISyntaxException {
        log.debug("REST request to update TypeChamps : {}", typeChamps);
        if (typeChamps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeChamps result = typeChampsService.save(typeChamps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeChamps.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-champs} : get all the typeChamps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeChamps in body.
     */
    @GetMapping("/type-champs")
    public ResponseEntity<List<TypeChamps>> getAllTypeChamps(Pageable pageable) {
        log.debug("REST request to get a page of TypeChamps");
        Page<TypeChamps> page = typeChampsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-champs/:id} : get the "id" typeChamps.
     *
     * @param id the id of the typeChamps to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeChamps, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-champs/{id}")
    public ResponseEntity<TypeChamps> getTypeChamps(@PathVariable Long id) {
        log.debug("REST request to get TypeChamps : {}", id);
        Optional<TypeChamps> typeChamps = typeChampsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeChamps);
    }

    /**
     * {@code DELETE  /type-champs/:id} : delete the "id" typeChamps.
     *
     * @param id the id of the typeChamps to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-champs/{id}")
    public ResponseEntity<Void> deleteTypeChamps(@PathVariable Long id) {
        log.debug("REST request to delete TypeChamps : {}", id);
        typeChampsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
