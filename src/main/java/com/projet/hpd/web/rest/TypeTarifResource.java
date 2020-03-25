package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeTarif;
import com.projet.hpd.service.TypeTarifService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeTarif}.
 */
@RestController
@RequestMapping("/api")
public class TypeTarifResource {

    private final Logger log = LoggerFactory.getLogger(TypeTarifResource.class);

    private static final String ENTITY_NAME = "typeTarif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeTarifService typeTarifService;

    public TypeTarifResource(TypeTarifService typeTarifService) {
        this.typeTarifService = typeTarifService;
    }

    /**
     * {@code POST  /type-tarifs} : Create a new typeTarif.
     *
     * @param typeTarif the typeTarif to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeTarif, or with status {@code 400 (Bad Request)} if the typeTarif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-tarifs")
    public ResponseEntity<TypeTarif> createTypeTarif(@RequestBody TypeTarif typeTarif) throws URISyntaxException {
        log.debug("REST request to save TypeTarif : {}", typeTarif);
        if (typeTarif.getId() != null) {
            throw new BadRequestAlertException("A new typeTarif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTarif result = typeTarifService.save(typeTarif);
        return ResponseEntity.created(new URI("/api/type-tarifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-tarifs} : Updates an existing typeTarif.
     *
     * @param typeTarif the typeTarif to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeTarif,
     * or with status {@code 400 (Bad Request)} if the typeTarif is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeTarif couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-tarifs")
    public ResponseEntity<TypeTarif> updateTypeTarif(@RequestBody TypeTarif typeTarif) throws URISyntaxException {
        log.debug("REST request to update TypeTarif : {}", typeTarif);
        if (typeTarif.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeTarif result = typeTarifService.save(typeTarif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeTarif.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-tarifs} : get all the typeTarifs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeTarifs in body.
     */
    @GetMapping("/type-tarifs")
    public ResponseEntity<List<TypeTarif>> getAllTypeTarifs(Pageable pageable) {
        log.debug("REST request to get a page of TypeTarifs");
        Page<TypeTarif> page = typeTarifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-tarifs/:id} : get the "id" typeTarif.
     *
     * @param id the id of the typeTarif to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeTarif, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-tarifs/{id}")
    public ResponseEntity<TypeTarif> getTypeTarif(@PathVariable Long id) {
        log.debug("REST request to get TypeTarif : {}", id);
        Optional<TypeTarif> typeTarif = typeTarifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeTarif);
    }

    /**
     * {@code DELETE  /type-tarifs/:id} : delete the "id" typeTarif.
     *
     * @param id the id of the typeTarif to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-tarifs/{id}")
    public ResponseEntity<Void> deleteTypeTarif(@PathVariable Long id) {
        log.debug("REST request to delete TypeTarif : {}", id);
        typeTarifService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
