package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeSoins;
import com.projet.hpd.service.TypeSoinsService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeSoins}.
 */
@RestController
@RequestMapping("/api")
public class TypeSoinsResource {

    private final Logger log = LoggerFactory.getLogger(TypeSoinsResource.class);

    private static final String ENTITY_NAME = "typeSoins";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeSoinsService typeSoinsService;

    public TypeSoinsResource(TypeSoinsService typeSoinsService) {
        this.typeSoinsService = typeSoinsService;
    }

    /**
     * {@code POST  /type-soins} : Create a new typeSoins.
     *
     * @param typeSoins the typeSoins to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeSoins, or with status {@code 400 (Bad Request)} if the typeSoins has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-soins")
    public ResponseEntity<TypeSoins> createTypeSoins(@RequestBody TypeSoins typeSoins) throws URISyntaxException {
        log.debug("REST request to save TypeSoins : {}", typeSoins);
        if (typeSoins.getId() != null) {
            throw new BadRequestAlertException("A new typeSoins cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeSoins result = typeSoinsService.save(typeSoins);
        return ResponseEntity.created(new URI("/api/type-soins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-soins} : Updates an existing typeSoins.
     *
     * @param typeSoins the typeSoins to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeSoins,
     * or with status {@code 400 (Bad Request)} if the typeSoins is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeSoins couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-soins")
    public ResponseEntity<TypeSoins> updateTypeSoins(@RequestBody TypeSoins typeSoins) throws URISyntaxException {
        log.debug("REST request to update TypeSoins : {}", typeSoins);
        if (typeSoins.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeSoins result = typeSoinsService.save(typeSoins);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeSoins.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-soins} : get all the typeSoins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeSoins in body.
     */
    @GetMapping("/type-soins")
    public ResponseEntity<List<TypeSoins>> getAllTypeSoins(Pageable pageable) {
        log.debug("REST request to get a page of TypeSoins");
        Page<TypeSoins> page = typeSoinsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-soins/:id} : get the "id" typeSoins.
     *
     * @param id the id of the typeSoins to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeSoins, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-soins/{id}")
    public ResponseEntity<TypeSoins> getTypeSoins(@PathVariable Long id) {
        log.debug("REST request to get TypeSoins : {}", id);
        Optional<TypeSoins> typeSoins = typeSoinsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeSoins);
    }

    /**
     * {@code DELETE  /type-soins/:id} : delete the "id" typeSoins.
     *
     * @param id the id of the typeSoins to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-soins/{id}")
    public ResponseEntity<Void> deleteTypeSoins(@PathVariable Long id) {
        log.debug("REST request to delete TypeSoins : {}", id);
        typeSoinsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
