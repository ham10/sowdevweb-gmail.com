package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TabAmortis;
import com.projet.hpd.service.TabAmortisService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TabAmortis}.
 */
@RestController
@RequestMapping("/api")
public class TabAmortisResource {

    private final Logger log = LoggerFactory.getLogger(TabAmortisResource.class);

    private static final String ENTITY_NAME = "tabAmortis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TabAmortisService tabAmortisService;

    public TabAmortisResource(TabAmortisService tabAmortisService) {
        this.tabAmortisService = tabAmortisService;
    }

    /**
     * {@code POST  /tab-amortis} : Create a new tabAmortis.
     *
     * @param tabAmortis the tabAmortis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tabAmortis, or with status {@code 400 (Bad Request)} if the tabAmortis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tab-amortis")
    public ResponseEntity<TabAmortis> createTabAmortis(@RequestBody TabAmortis tabAmortis) throws URISyntaxException {
        log.debug("REST request to save TabAmortis : {}", tabAmortis);
        if (tabAmortis.getId() != null) {
            throw new BadRequestAlertException("A new tabAmortis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TabAmortis result = tabAmortisService.save(tabAmortis);
        return ResponseEntity.created(new URI("/api/tab-amortis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tab-amortis} : Updates an existing tabAmortis.
     *
     * @param tabAmortis the tabAmortis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tabAmortis,
     * or with status {@code 400 (Bad Request)} if the tabAmortis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tabAmortis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tab-amortis")
    public ResponseEntity<TabAmortis> updateTabAmortis(@RequestBody TabAmortis tabAmortis) throws URISyntaxException {
        log.debug("REST request to update TabAmortis : {}", tabAmortis);
        if (tabAmortis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TabAmortis result = tabAmortisService.save(tabAmortis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tabAmortis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tab-amortis} : get all the tabAmortis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tabAmortis in body.
     */
    @GetMapping("/tab-amortis")
    public ResponseEntity<List<TabAmortis>> getAllTabAmortis(Pageable pageable) {
        log.debug("REST request to get a page of TabAmortis");
        Page<TabAmortis> page = tabAmortisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tab-amortis/:id} : get the "id" tabAmortis.
     *
     * @param id the id of the tabAmortis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tabAmortis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tab-amortis/{id}")
    public ResponseEntity<TabAmortis> getTabAmortis(@PathVariable Long id) {
        log.debug("REST request to get TabAmortis : {}", id);
        Optional<TabAmortis> tabAmortis = tabAmortisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tabAmortis);
    }

    /**
     * {@code DELETE  /tab-amortis/:id} : delete the "id" tabAmortis.
     *
     * @param id the id of the tabAmortis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tab-amortis/{id}")
    public ResponseEntity<Void> deleteTabAmortis(@PathVariable Long id) {
        log.debug("REST request to delete TabAmortis : {}", id);
        tabAmortisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
