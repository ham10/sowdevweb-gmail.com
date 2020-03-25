package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeMvtStock;
import com.projet.hpd.service.TypeMvtStockService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeMvtStock}.
 */
@RestController
@RequestMapping("/api")
public class TypeMvtStockResource {

    private final Logger log = LoggerFactory.getLogger(TypeMvtStockResource.class);

    private static final String ENTITY_NAME = "typeMvtStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeMvtStockService typeMvtStockService;

    public TypeMvtStockResource(TypeMvtStockService typeMvtStockService) {
        this.typeMvtStockService = typeMvtStockService;
    }

    /**
     * {@code POST  /type-mvt-stocks} : Create a new typeMvtStock.
     *
     * @param typeMvtStock the typeMvtStock to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeMvtStock, or with status {@code 400 (Bad Request)} if the typeMvtStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-mvt-stocks")
    public ResponseEntity<TypeMvtStock> createTypeMvtStock(@RequestBody TypeMvtStock typeMvtStock) throws URISyntaxException {
        log.debug("REST request to save TypeMvtStock : {}", typeMvtStock);
        if (typeMvtStock.getId() != null) {
            throw new BadRequestAlertException("A new typeMvtStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeMvtStock result = typeMvtStockService.save(typeMvtStock);
        return ResponseEntity.created(new URI("/api/type-mvt-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-mvt-stocks} : Updates an existing typeMvtStock.
     *
     * @param typeMvtStock the typeMvtStock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeMvtStock,
     * or with status {@code 400 (Bad Request)} if the typeMvtStock is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeMvtStock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-mvt-stocks")
    public ResponseEntity<TypeMvtStock> updateTypeMvtStock(@RequestBody TypeMvtStock typeMvtStock) throws URISyntaxException {
        log.debug("REST request to update TypeMvtStock : {}", typeMvtStock);
        if (typeMvtStock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeMvtStock result = typeMvtStockService.save(typeMvtStock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeMvtStock.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-mvt-stocks} : get all the typeMvtStocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeMvtStocks in body.
     */
    @GetMapping("/type-mvt-stocks")
    public ResponseEntity<List<TypeMvtStock>> getAllTypeMvtStocks(Pageable pageable) {
        log.debug("REST request to get a page of TypeMvtStocks");
        Page<TypeMvtStock> page = typeMvtStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-mvt-stocks/:id} : get the "id" typeMvtStock.
     *
     * @param id the id of the typeMvtStock to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeMvtStock, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-mvt-stocks/{id}")
    public ResponseEntity<TypeMvtStock> getTypeMvtStock(@PathVariable Long id) {
        log.debug("REST request to get TypeMvtStock : {}", id);
        Optional<TypeMvtStock> typeMvtStock = typeMvtStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeMvtStock);
    }

    /**
     * {@code DELETE  /type-mvt-stocks/:id} : delete the "id" typeMvtStock.
     *
     * @param id the id of the typeMvtStock to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-mvt-stocks/{id}")
    public ResponseEntity<Void> deleteTypeMvtStock(@PathVariable Long id) {
        log.debug("REST request to delete TypeMvtStock : {}", id);
        typeMvtStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
