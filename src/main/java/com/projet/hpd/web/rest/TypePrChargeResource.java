package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypePrCharge;
import com.projet.hpd.service.TypePrChargeService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypePrCharge}.
 */
@RestController
@RequestMapping("/api")
public class TypePrChargeResource {

    private final Logger log = LoggerFactory.getLogger(TypePrChargeResource.class);

    private static final String ENTITY_NAME = "typePrCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePrChargeService typePrChargeService;

    public TypePrChargeResource(TypePrChargeService typePrChargeService) {
        this.typePrChargeService = typePrChargeService;
    }

    /**
     * {@code POST  /type-pr-charges} : Create a new typePrCharge.
     *
     * @param typePrCharge the typePrCharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePrCharge, or with status {@code 400 (Bad Request)} if the typePrCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-pr-charges")
    public ResponseEntity<TypePrCharge> createTypePrCharge(@RequestBody TypePrCharge typePrCharge) throws URISyntaxException {
        log.debug("REST request to save TypePrCharge : {}", typePrCharge);
        if (typePrCharge.getId() != null) {
            throw new BadRequestAlertException("A new typePrCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePrCharge result = typePrChargeService.save(typePrCharge);
        return ResponseEntity.created(new URI("/api/type-pr-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-pr-charges} : Updates an existing typePrCharge.
     *
     * @param typePrCharge the typePrCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePrCharge,
     * or with status {@code 400 (Bad Request)} if the typePrCharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePrCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-pr-charges")
    public ResponseEntity<TypePrCharge> updateTypePrCharge(@RequestBody TypePrCharge typePrCharge) throws URISyntaxException {
        log.debug("REST request to update TypePrCharge : {}", typePrCharge);
        if (typePrCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePrCharge result = typePrChargeService.save(typePrCharge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePrCharge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-pr-charges} : get all the typePrCharges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePrCharges in body.
     */
    @GetMapping("/type-pr-charges")
    public ResponseEntity<List<TypePrCharge>> getAllTypePrCharges(Pageable pageable) {
        log.debug("REST request to get a page of TypePrCharges");
        Page<TypePrCharge> page = typePrChargeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-pr-charges/:id} : get the "id" typePrCharge.
     *
     * @param id the id of the typePrCharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePrCharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-pr-charges/{id}")
    public ResponseEntity<TypePrCharge> getTypePrCharge(@PathVariable Long id) {
        log.debug("REST request to get TypePrCharge : {}", id);
        Optional<TypePrCharge> typePrCharge = typePrChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePrCharge);
    }

    /**
     * {@code DELETE  /type-pr-charges/:id} : delete the "id" typePrCharge.
     *
     * @param id the id of the typePrCharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-pr-charges/{id}")
    public ResponseEntity<Void> deleteTypePrCharge(@PathVariable Long id) {
        log.debug("REST request to delete TypePrCharge : {}", id);
        typePrChargeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
