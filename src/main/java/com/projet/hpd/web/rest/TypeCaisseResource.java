package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeCaisse;
import com.projet.hpd.service.TypeCaisseService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeCaisse}.
 */
@RestController
@RequestMapping("/api")
public class TypeCaisseResource {

    private final Logger log = LoggerFactory.getLogger(TypeCaisseResource.class);

    private static final String ENTITY_NAME = "typeCaisse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeCaisseService typeCaisseService;

    public TypeCaisseResource(TypeCaisseService typeCaisseService) {
        this.typeCaisseService = typeCaisseService;
    }

    /**
     * {@code POST  /type-caisses} : Create a new typeCaisse.
     *
     * @param typeCaisse the typeCaisse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeCaisse, or with status {@code 400 (Bad Request)} if the typeCaisse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-caisses")
    public ResponseEntity<TypeCaisse> createTypeCaisse(@RequestBody TypeCaisse typeCaisse) throws URISyntaxException {
        log.debug("REST request to save TypeCaisse : {}", typeCaisse);
        if (typeCaisse.getId() != null) {
            throw new BadRequestAlertException("A new typeCaisse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCaisse result = typeCaisseService.save(typeCaisse);
        return ResponseEntity.created(new URI("/api/type-caisses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-caisses} : Updates an existing typeCaisse.
     *
     * @param typeCaisse the typeCaisse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeCaisse,
     * or with status {@code 400 (Bad Request)} if the typeCaisse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeCaisse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-caisses")
    public ResponseEntity<TypeCaisse> updateTypeCaisse(@RequestBody TypeCaisse typeCaisse) throws URISyntaxException {
        log.debug("REST request to update TypeCaisse : {}", typeCaisse);
        if (typeCaisse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeCaisse result = typeCaisseService.save(typeCaisse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeCaisse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-caisses} : get all the typeCaisses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeCaisses in body.
     */
    @GetMapping("/type-caisses")
    public ResponseEntity<List<TypeCaisse>> getAllTypeCaisses(Pageable pageable) {
        log.debug("REST request to get a page of TypeCaisses");
        Page<TypeCaisse> page = typeCaisseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-caisses/:id} : get the "id" typeCaisse.
     *
     * @param id the id of the typeCaisse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeCaisse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-caisses/{id}")
    public ResponseEntity<TypeCaisse> getTypeCaisse(@PathVariable Long id) {
        log.debug("REST request to get TypeCaisse : {}", id);
        Optional<TypeCaisse> typeCaisse = typeCaisseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeCaisse);
    }

    /**
     * {@code DELETE  /type-caisses/:id} : delete the "id" typeCaisse.
     *
     * @param id the id of the typeCaisse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-caisses/{id}")
    public ResponseEntity<Void> deleteTypeCaisse(@PathVariable Long id) {
        log.debug("REST request to delete TypeCaisse : {}", id);
        typeCaisseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
