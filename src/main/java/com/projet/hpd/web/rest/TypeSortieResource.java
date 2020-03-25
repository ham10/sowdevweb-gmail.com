package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeSortie;
import com.projet.hpd.service.TypeSortieService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeSortie}.
 */
@RestController
@RequestMapping("/api")
public class TypeSortieResource {

    private final Logger log = LoggerFactory.getLogger(TypeSortieResource.class);

    private static final String ENTITY_NAME = "typeSortie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeSortieService typeSortieService;

    public TypeSortieResource(TypeSortieService typeSortieService) {
        this.typeSortieService = typeSortieService;
    }

    /**
     * {@code POST  /type-sorties} : Create a new typeSortie.
     *
     * @param typeSortie the typeSortie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeSortie, or with status {@code 400 (Bad Request)} if the typeSortie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-sorties")
    public ResponseEntity<TypeSortie> createTypeSortie(@RequestBody TypeSortie typeSortie) throws URISyntaxException {
        log.debug("REST request to save TypeSortie : {}", typeSortie);
        if (typeSortie.getId() != null) {
            throw new BadRequestAlertException("A new typeSortie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeSortie result = typeSortieService.save(typeSortie);
        return ResponseEntity.created(new URI("/api/type-sorties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-sorties} : Updates an existing typeSortie.
     *
     * @param typeSortie the typeSortie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeSortie,
     * or with status {@code 400 (Bad Request)} if the typeSortie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeSortie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-sorties")
    public ResponseEntity<TypeSortie> updateTypeSortie(@RequestBody TypeSortie typeSortie) throws URISyntaxException {
        log.debug("REST request to update TypeSortie : {}", typeSortie);
        if (typeSortie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeSortie result = typeSortieService.save(typeSortie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeSortie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-sorties} : get all the typeSorties.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeSorties in body.
     */
    @GetMapping("/type-sorties")
    public ResponseEntity<List<TypeSortie>> getAllTypeSorties(Pageable pageable) {
        log.debug("REST request to get a page of TypeSorties");
        Page<TypeSortie> page = typeSortieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-sorties/:id} : get the "id" typeSortie.
     *
     * @param id the id of the typeSortie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeSortie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-sorties/{id}")
    public ResponseEntity<TypeSortie> getTypeSortie(@PathVariable Long id) {
        log.debug("REST request to get TypeSortie : {}", id);
        Optional<TypeSortie> typeSortie = typeSortieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeSortie);
    }

    /**
     * {@code DELETE  /type-sorties/:id} : delete the "id" typeSortie.
     *
     * @param id the id of the typeSortie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-sorties/{id}")
    public ResponseEntity<Void> deleteTypeSortie(@PathVariable Long id) {
        log.debug("REST request to delete TypeSortie : {}", id);
        typeSortieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
