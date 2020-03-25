package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypePlateau;
import com.projet.hpd.service.TypePlateauService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypePlateau}.
 */
@RestController
@RequestMapping("/api")
public class TypePlateauResource {

    private final Logger log = LoggerFactory.getLogger(TypePlateauResource.class);

    private static final String ENTITY_NAME = "typePlateau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePlateauService typePlateauService;

    public TypePlateauResource(TypePlateauService typePlateauService) {
        this.typePlateauService = typePlateauService;
    }

    /**
     * {@code POST  /type-plateaus} : Create a new typePlateau.
     *
     * @param typePlateau the typePlateau to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePlateau, or with status {@code 400 (Bad Request)} if the typePlateau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-plateaus")
    public ResponseEntity<TypePlateau> createTypePlateau(@RequestBody TypePlateau typePlateau) throws URISyntaxException {
        log.debug("REST request to save TypePlateau : {}", typePlateau);
        if (typePlateau.getId() != null) {
            throw new BadRequestAlertException("A new typePlateau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePlateau result = typePlateauService.save(typePlateau);
        return ResponseEntity.created(new URI("/api/type-plateaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-plateaus} : Updates an existing typePlateau.
     *
     * @param typePlateau the typePlateau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePlateau,
     * or with status {@code 400 (Bad Request)} if the typePlateau is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePlateau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-plateaus")
    public ResponseEntity<TypePlateau> updateTypePlateau(@RequestBody TypePlateau typePlateau) throws URISyntaxException {
        log.debug("REST request to update TypePlateau : {}", typePlateau);
        if (typePlateau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePlateau result = typePlateauService.save(typePlateau);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePlateau.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-plateaus} : get all the typePlateaus.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePlateaus in body.
     */
    @GetMapping("/type-plateaus")
    public ResponseEntity<List<TypePlateau>> getAllTypePlateaus(Pageable pageable) {
        log.debug("REST request to get a page of TypePlateaus");
        Page<TypePlateau> page = typePlateauService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-plateaus/:id} : get the "id" typePlateau.
     *
     * @param id the id of the typePlateau to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePlateau, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-plateaus/{id}")
    public ResponseEntity<TypePlateau> getTypePlateau(@PathVariable Long id) {
        log.debug("REST request to get TypePlateau : {}", id);
        Optional<TypePlateau> typePlateau = typePlateauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePlateau);
    }

    /**
     * {@code DELETE  /type-plateaus/:id} : delete the "id" typePlateau.
     *
     * @param id the id of the typePlateau to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-plateaus/{id}")
    public ResponseEntity<Void> deleteTypePlateau(@PathVariable Long id) {
        log.debug("REST request to delete TypePlateau : {}", id);
        typePlateauService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
