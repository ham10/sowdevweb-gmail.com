package com.projet.hpd.web.rest;

import com.projet.hpd.domain.ProdFournis;
import com.projet.hpd.service.ProdFournisService;
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
 * REST controller for managing {@link com.projet.hpd.domain.ProdFournis}.
 */
@RestController
@RequestMapping("/api")
public class ProdFournisResource {

    private final Logger log = LoggerFactory.getLogger(ProdFournisResource.class);

    private static final String ENTITY_NAME = "prodFournis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdFournisService prodFournisService;

    public ProdFournisResource(ProdFournisService prodFournisService) {
        this.prodFournisService = prodFournisService;
    }

    /**
     * {@code POST  /prod-fournis} : Create a new prodFournis.
     *
     * @param prodFournis the prodFournis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prodFournis, or with status {@code 400 (Bad Request)} if the prodFournis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prod-fournis")
    public ResponseEntity<ProdFournis> createProdFournis(@RequestBody ProdFournis prodFournis) throws URISyntaxException {
        log.debug("REST request to save ProdFournis : {}", prodFournis);
        if (prodFournis.getId() != null) {
            throw new BadRequestAlertException("A new prodFournis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdFournis result = prodFournisService.save(prodFournis);
        return ResponseEntity.created(new URI("/api/prod-fournis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prod-fournis} : Updates an existing prodFournis.
     *
     * @param prodFournis the prodFournis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodFournis,
     * or with status {@code 400 (Bad Request)} if the prodFournis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prodFournis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prod-fournis")
    public ResponseEntity<ProdFournis> updateProdFournis(@RequestBody ProdFournis prodFournis) throws URISyntaxException {
        log.debug("REST request to update ProdFournis : {}", prodFournis);
        if (prodFournis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProdFournis result = prodFournisService.save(prodFournis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodFournis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prod-fournis} : get all the prodFournis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prodFournis in body.
     */
    @GetMapping("/prod-fournis")
    public ResponseEntity<List<ProdFournis>> getAllProdFournis(Pageable pageable) {
        log.debug("REST request to get a page of ProdFournis");
        Page<ProdFournis> page = prodFournisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prod-fournis/:id} : get the "id" prodFournis.
     *
     * @param id the id of the prodFournis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prodFournis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prod-fournis/{id}")
    public ResponseEntity<ProdFournis> getProdFournis(@PathVariable Long id) {
        log.debug("REST request to get ProdFournis : {}", id);
        Optional<ProdFournis> prodFournis = prodFournisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prodFournis);
    }

    /**
     * {@code DELETE  /prod-fournis/:id} : delete the "id" prodFournis.
     *
     * @param id the id of the prodFournis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prod-fournis/{id}")
    public ResponseEntity<Void> deleteProdFournis(@PathVariable Long id) {
        log.debug("REST request to delete ProdFournis : {}", id);
        prodFournisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
