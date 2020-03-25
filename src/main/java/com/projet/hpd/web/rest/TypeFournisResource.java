package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeFournis;
import com.projet.hpd.service.TypeFournisService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeFournis}.
 */
@RestController
@RequestMapping("/api")
public class TypeFournisResource {

    private final Logger log = LoggerFactory.getLogger(TypeFournisResource.class);

    private static final String ENTITY_NAME = "typeFournis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeFournisService typeFournisService;

    public TypeFournisResource(TypeFournisService typeFournisService) {
        this.typeFournisService = typeFournisService;
    }

    /**
     * {@code POST  /type-fournis} : Create a new typeFournis.
     *
     * @param typeFournis the typeFournis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeFournis, or with status {@code 400 (Bad Request)} if the typeFournis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-fournis")
    public ResponseEntity<TypeFournis> createTypeFournis(@RequestBody TypeFournis typeFournis) throws URISyntaxException {
        log.debug("REST request to save TypeFournis : {}", typeFournis);
        if (typeFournis.getId() != null) {
            throw new BadRequestAlertException("A new typeFournis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeFournis result = typeFournisService.save(typeFournis);
        return ResponseEntity.created(new URI("/api/type-fournis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-fournis} : Updates an existing typeFournis.
     *
     * @param typeFournis the typeFournis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeFournis,
     * or with status {@code 400 (Bad Request)} if the typeFournis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeFournis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-fournis")
    public ResponseEntity<TypeFournis> updateTypeFournis(@RequestBody TypeFournis typeFournis) throws URISyntaxException {
        log.debug("REST request to update TypeFournis : {}", typeFournis);
        if (typeFournis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeFournis result = typeFournisService.save(typeFournis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeFournis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-fournis} : get all the typeFournis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeFournis in body.
     */
    @GetMapping("/type-fournis")
    public ResponseEntity<List<TypeFournis>> getAllTypeFournis(Pageable pageable) {
        log.debug("REST request to get a page of TypeFournis");
        Page<TypeFournis> page = typeFournisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-fournis/:id} : get the "id" typeFournis.
     *
     * @param id the id of the typeFournis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeFournis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-fournis/{id}")
    public ResponseEntity<TypeFournis> getTypeFournis(@PathVariable Long id) {
        log.debug("REST request to get TypeFournis : {}", id);
        Optional<TypeFournis> typeFournis = typeFournisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeFournis);
    }

    /**
     * {@code DELETE  /type-fournis/:id} : delete the "id" typeFournis.
     *
     * @param id the id of the typeFournis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-fournis/{id}")
    public ResponseEntity<Void> deleteTypeFournis(@PathVariable Long id) {
        log.debug("REST request to delete TypeFournis : {}", id);
        typeFournisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
