package com.projet.hpd.web.rest;

import com.projet.hpd.domain.CatMagasin;
import com.projet.hpd.service.CatMagasinService;
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
 * REST controller for managing {@link com.projet.hpd.domain.CatMagasin}.
 */
@RestController
@RequestMapping("/api")
public class CatMagasinResource {

    private final Logger log = LoggerFactory.getLogger(CatMagasinResource.class);

    private static final String ENTITY_NAME = "catMagasin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatMagasinService catMagasinService;

    public CatMagasinResource(CatMagasinService catMagasinService) {
        this.catMagasinService = catMagasinService;
    }

    /**
     * {@code POST  /cat-magasins} : Create a new catMagasin.
     *
     * @param catMagasin the catMagasin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catMagasin, or with status {@code 400 (Bad Request)} if the catMagasin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-magasins")
    public ResponseEntity<CatMagasin> createCatMagasin(@RequestBody CatMagasin catMagasin) throws URISyntaxException {
        log.debug("REST request to save CatMagasin : {}", catMagasin);
        if (catMagasin.getId() != null) {
            throw new BadRequestAlertException("A new catMagasin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatMagasin result = catMagasinService.save(catMagasin);
        return ResponseEntity.created(new URI("/api/cat-magasins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-magasins} : Updates an existing catMagasin.
     *
     * @param catMagasin the catMagasin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catMagasin,
     * or with status {@code 400 (Bad Request)} if the catMagasin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catMagasin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-magasins")
    public ResponseEntity<CatMagasin> updateCatMagasin(@RequestBody CatMagasin catMagasin) throws URISyntaxException {
        log.debug("REST request to update CatMagasin : {}", catMagasin);
        if (catMagasin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatMagasin result = catMagasinService.save(catMagasin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catMagasin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-magasins} : get all the catMagasins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catMagasins in body.
     */
    @GetMapping("/cat-magasins")
    public ResponseEntity<List<CatMagasin>> getAllCatMagasins(Pageable pageable) {
        log.debug("REST request to get a page of CatMagasins");
        Page<CatMagasin> page = catMagasinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-magasins/:id} : get the "id" catMagasin.
     *
     * @param id the id of the catMagasin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catMagasin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-magasins/{id}")
    public ResponseEntity<CatMagasin> getCatMagasin(@PathVariable Long id) {
        log.debug("REST request to get CatMagasin : {}", id);
        Optional<CatMagasin> catMagasin = catMagasinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catMagasin);
    }

    /**
     * {@code DELETE  /cat-magasins/:id} : delete the "id" catMagasin.
     *
     * @param id the id of the catMagasin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-magasins/{id}")
    public ResponseEntity<Void> deleteCatMagasin(@PathVariable Long id) {
        log.debug("REST request to delete CatMagasin : {}", id);
        catMagasinService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
