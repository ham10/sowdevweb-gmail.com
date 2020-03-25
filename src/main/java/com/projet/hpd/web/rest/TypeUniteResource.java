package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeUnite;
import com.projet.hpd.service.TypeUniteService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeUnite}.
 */
@RestController
@RequestMapping("/api")
public class TypeUniteResource {

    private final Logger log = LoggerFactory.getLogger(TypeUniteResource.class);

    private static final String ENTITY_NAME = "typeUnite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeUniteService typeUniteService;

    public TypeUniteResource(TypeUniteService typeUniteService) {
        this.typeUniteService = typeUniteService;
    }

    /**
     * {@code POST  /type-unites} : Create a new typeUnite.
     *
     * @param typeUnite the typeUnite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeUnite, or with status {@code 400 (Bad Request)} if the typeUnite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-unites")
    public ResponseEntity<TypeUnite> createTypeUnite(@RequestBody TypeUnite typeUnite) throws URISyntaxException {
        log.debug("REST request to save TypeUnite : {}", typeUnite);
        if (typeUnite.getId() != null) {
            throw new BadRequestAlertException("A new typeUnite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeUnite result = typeUniteService.save(typeUnite);
        return ResponseEntity.created(new URI("/api/type-unites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-unites} : Updates an existing typeUnite.
     *
     * @param typeUnite the typeUnite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeUnite,
     * or with status {@code 400 (Bad Request)} if the typeUnite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeUnite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-unites")
    public ResponseEntity<TypeUnite> updateTypeUnite(@RequestBody TypeUnite typeUnite) throws URISyntaxException {
        log.debug("REST request to update TypeUnite : {}", typeUnite);
        if (typeUnite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeUnite result = typeUniteService.save(typeUnite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeUnite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-unites} : get all the typeUnites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeUnites in body.
     */
    @GetMapping("/type-unites")
    public ResponseEntity<List<TypeUnite>> getAllTypeUnites(Pageable pageable) {
        log.debug("REST request to get a page of TypeUnites");
        Page<TypeUnite> page = typeUniteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-unites/:id} : get the "id" typeUnite.
     *
     * @param id the id of the typeUnite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeUnite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-unites/{id}")
    public ResponseEntity<TypeUnite> getTypeUnite(@PathVariable Long id) {
        log.debug("REST request to get TypeUnite : {}", id);
        Optional<TypeUnite> typeUnite = typeUniteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeUnite);
    }

    /**
     * {@code DELETE  /type-unites/:id} : delete the "id" typeUnite.
     *
     * @param id the id of the typeUnite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-unites/{id}")
    public ResponseEntity<Void> deleteTypeUnite(@PathVariable Long id) {
        log.debug("REST request to delete TypeUnite : {}", id);
        typeUniteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
