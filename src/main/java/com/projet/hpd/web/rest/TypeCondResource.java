package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeCond;
import com.projet.hpd.service.TypeCondService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeCond}.
 */
@RestController
@RequestMapping("/api")
public class TypeCondResource {

    private final Logger log = LoggerFactory.getLogger(TypeCondResource.class);

    private static final String ENTITY_NAME = "typeCond";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeCondService typeCondService;

    public TypeCondResource(TypeCondService typeCondService) {
        this.typeCondService = typeCondService;
    }

    /**
     * {@code POST  /type-conds} : Create a new typeCond.
     *
     * @param typeCond the typeCond to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeCond, or with status {@code 400 (Bad Request)} if the typeCond has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-conds")
    public ResponseEntity<TypeCond> createTypeCond(@RequestBody TypeCond typeCond) throws URISyntaxException {
        log.debug("REST request to save TypeCond : {}", typeCond);
        if (typeCond.getId() != null) {
            throw new BadRequestAlertException("A new typeCond cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCond result = typeCondService.save(typeCond);
        return ResponseEntity.created(new URI("/api/type-conds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-conds} : Updates an existing typeCond.
     *
     * @param typeCond the typeCond to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeCond,
     * or with status {@code 400 (Bad Request)} if the typeCond is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeCond couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-conds")
    public ResponseEntity<TypeCond> updateTypeCond(@RequestBody TypeCond typeCond) throws URISyntaxException {
        log.debug("REST request to update TypeCond : {}", typeCond);
        if (typeCond.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCond result = typeCondService.save(typeCond);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeCond.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-conds} : get all the typeConds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeConds in body.
     */
    @GetMapping("/type-conds")
    public ResponseEntity<List<TypeCond>> getAllTypeConds(Pageable pageable) {
        log.debug("REST request to get a page of TypeConds");
        Page<TypeCond> page = typeCondService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-conds/:id} : get the "id" typeCond.
     *
     * @param id the id of the typeCond to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeCond, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-conds/{id}")
    public ResponseEntity<TypeCond> getTypeCond(@PathVariable Long id) {
        log.debug("REST request to get TypeCond : {}", id);
        Optional<TypeCond> typeCond = typeCondService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCond);
    }

    /**
     * {@code DELETE  /type-conds/:id} : delete the "id" typeCond.
     *
     * @param id the id of the typeCond to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-conds/{id}")
    public ResponseEntity<Void> deleteTypeCond(@PathVariable Long id) {
        log.debug("REST request to delete TypeCond : {}", id);
        typeCondService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
