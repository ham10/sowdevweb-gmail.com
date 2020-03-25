package com.projet.hpd.web.rest;

import com.projet.hpd.domain.FormeProd;
import com.projet.hpd.service.FormeProdService;
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
 * REST controller for managing {@link com.projet.hpd.domain.FormeProd}.
 */
@RestController
@RequestMapping("/api")
public class FormeProdResource {

    private final Logger log = LoggerFactory.getLogger(FormeProdResource.class);

    private static final String ENTITY_NAME = "formeProd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormeProdService formeProdService;

    public FormeProdResource(FormeProdService formeProdService) {
        this.formeProdService = formeProdService;
    }

    /**
     * {@code POST  /forme-prods} : Create a new formeProd.
     *
     * @param formeProd the formeProd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formeProd, or with status {@code 400 (Bad Request)} if the formeProd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forme-prods")
    public ResponseEntity<FormeProd> createFormeProd(@RequestBody FormeProd formeProd) throws URISyntaxException {
        log.debug("REST request to save FormeProd : {}", formeProd);
        if (formeProd.getId() != null) {
            throw new BadRequestAlertException("A new formeProd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormeProd result = formeProdService.save(formeProd);
        return ResponseEntity.created(new URI("/api/forme-prods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forme-prods} : Updates an existing formeProd.
     *
     * @param formeProd the formeProd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formeProd,
     * or with status {@code 400 (Bad Request)} if the formeProd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formeProd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forme-prods")
    public ResponseEntity<FormeProd> updateFormeProd(@RequestBody FormeProd formeProd) throws URISyntaxException {
        log.debug("REST request to update FormeProd : {}", formeProd);
        if (formeProd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormeProd result = formeProdService.save(formeProd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formeProd.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forme-prods} : get all the formeProds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formeProds in body.
     */
    @GetMapping("/forme-prods")
    public ResponseEntity<List<FormeProd>> getAllFormeProds(Pageable pageable) {
        log.debug("REST request to get a page of FormeProds");
        Page<FormeProd> page = formeProdService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forme-prods/:id} : get the "id" formeProd.
     *
     * @param id the id of the formeProd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formeProd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forme-prods/{id}")
    public ResponseEntity<FormeProd> getFormeProd(@PathVariable Long id) {
        log.debug("REST request to get FormeProd : {}", id);
        Optional<FormeProd> formeProd = formeProdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formeProd);
    }

    /**
     * {@code DELETE  /forme-prods/:id} : delete the "id" formeProd.
     *
     * @param id the id of the formeProd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forme-prods/{id}")
    public ResponseEntity<Void> deleteFormeProd(@PathVariable Long id) {
        log.debug("REST request to delete FormeProd : {}", id);
        formeProdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
