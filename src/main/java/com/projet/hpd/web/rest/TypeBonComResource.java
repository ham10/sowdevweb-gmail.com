package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeBonCom;
import com.projet.hpd.service.TypeBonComService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeBonCom}.
 */
@RestController
@RequestMapping("/api")
public class TypeBonComResource {

    private final Logger log = LoggerFactory.getLogger(TypeBonComResource.class);

    private static final String ENTITY_NAME = "typeBonCom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeBonComService typeBonComService;

    public TypeBonComResource(TypeBonComService typeBonComService) {
        this.typeBonComService = typeBonComService;
    }

    /**
     * {@code POST  /type-bon-coms} : Create a new typeBonCom.
     *
     * @param typeBonCom the typeBonCom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeBonCom, or with status {@code 400 (Bad Request)} if the typeBonCom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-bon-coms")
    public ResponseEntity<TypeBonCom> createTypeBonCom(@RequestBody TypeBonCom typeBonCom) throws URISyntaxException {
        log.debug("REST request to save TypeBonCom : {}", typeBonCom);
        if (typeBonCom.getId() != null) {
            throw new BadRequestAlertException("A new typeBonCom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeBonCom result = typeBonComService.save(typeBonCom);
        return ResponseEntity.created(new URI("/api/type-bon-coms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-bon-coms} : Updates an existing typeBonCom.
     *
     * @param typeBonCom the typeBonCom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeBonCom,
     * or with status {@code 400 (Bad Request)} if the typeBonCom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeBonCom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-bon-coms")
    public ResponseEntity<TypeBonCom> updateTypeBonCom(@RequestBody TypeBonCom typeBonCom) throws URISyntaxException {
        log.debug("REST request to update TypeBonCom : {}", typeBonCom);
        if (typeBonCom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeBonCom result = typeBonComService.save(typeBonCom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeBonCom.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-bon-coms} : get all the typeBonComs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeBonComs in body.
     */
    @GetMapping("/type-bon-coms")
    public ResponseEntity<List<TypeBonCom>> getAllTypeBonComs(Pageable pageable) {
        log.debug("REST request to get a page of TypeBonComs");
        Page<TypeBonCom> page = typeBonComService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-bon-coms/:id} : get the "id" typeBonCom.
     *
     * @param id the id of the typeBonCom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeBonCom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-bon-coms/{id}")
    public ResponseEntity<TypeBonCom> getTypeBonCom(@PathVariable Long id) {
        log.debug("REST request to get TypeBonCom : {}", id);
        Optional<TypeBonCom> typeBonCom = typeBonComService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeBonCom);
    }

    /**
     * {@code DELETE  /type-bon-coms/:id} : delete the "id" typeBonCom.
     *
     * @param id the id of the typeBonCom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-bon-coms/{id}")
    public ResponseEntity<Void> deleteTypeBonCom(@PathVariable Long id) {
        log.debug("REST request to delete TypeBonCom : {}", id);
        typeBonComService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
