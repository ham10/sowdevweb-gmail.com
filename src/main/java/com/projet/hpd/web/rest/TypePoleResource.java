package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypePole;
import com.projet.hpd.service.TypePoleService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypePole}.
 */
@RestController
@RequestMapping("/api")
public class TypePoleResource {

    private final Logger log = LoggerFactory.getLogger(TypePoleResource.class);

    private static final String ENTITY_NAME = "typePole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePoleService typePoleService;

    public TypePoleResource(TypePoleService typePoleService) {
        this.typePoleService = typePoleService;
    }

    /**
     * {@code POST  /type-poles} : Create a new typePole.
     *
     * @param typePole the typePole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePole, or with status {@code 400 (Bad Request)} if the typePole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-poles")
    public ResponseEntity<TypePole> createTypePole(@RequestBody TypePole typePole) throws URISyntaxException {
        log.debug("REST request to save TypePole : {}", typePole);
        if (typePole.getId() != null) {
            throw new BadRequestAlertException("A new typePole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePole result = typePoleService.save(typePole);
        return ResponseEntity.created(new URI("/api/type-poles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-poles} : Updates an existing typePole.
     *
     * @param typePole the typePole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePole,
     * or with status {@code 400 (Bad Request)} if the typePole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-poles")
    public ResponseEntity<TypePole> updateTypePole(@RequestBody TypePole typePole) throws URISyntaxException {
        log.debug("REST request to update TypePole : {}", typePole);
        if (typePole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePole result = typePoleService.save(typePole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-poles} : get all the typePoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePoles in body.
     */
    @GetMapping("/type-poles")
    public ResponseEntity<List<TypePole>> getAllTypePoles(Pageable pageable) {
        log.debug("REST request to get a page of TypePoles");
        Page<TypePole> page = typePoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-poles/:id} : get the "id" typePole.
     *
     * @param id the id of the typePole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-poles/{id}")
    public ResponseEntity<TypePole> getTypePole(@PathVariable Long id) {
        log.debug("REST request to get TypePole : {}", id);
        Optional<TypePole> typePole = typePoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePole);
    }

    /**
     * {@code DELETE  /type-poles/:id} : delete the "id" typePole.
     *
     * @param id the id of the typePole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-poles/{id}")
    public ResponseEntity<Void> deleteTypePole(@PathVariable Long id) {
        log.debug("REST request to delete TypePole : {}", id);
        typePoleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
