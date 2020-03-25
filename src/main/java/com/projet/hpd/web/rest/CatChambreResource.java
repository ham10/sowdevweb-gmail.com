package com.projet.hpd.web.rest;

import com.projet.hpd.domain.CatChambre;
import com.projet.hpd.service.CatChambreService;
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
 * REST controller for managing {@link com.projet.hpd.domain.CatChambre}.
 */
@RestController
@RequestMapping("/api")
public class CatChambreResource {

    private final Logger log = LoggerFactory.getLogger(CatChambreResource.class);

    private static final String ENTITY_NAME = "catChambre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatChambreService catChambreService;

    public CatChambreResource(CatChambreService catChambreService) {
        this.catChambreService = catChambreService;
    }

    /**
     * {@code POST  /cat-chambres} : Create a new catChambre.
     *
     * @param catChambre the catChambre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catChambre, or with status {@code 400 (Bad Request)} if the catChambre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cat-chambres")
    public ResponseEntity<CatChambre> createCatChambre(@RequestBody CatChambre catChambre) throws URISyntaxException {
        log.debug("REST request to save CatChambre : {}", catChambre);
        if (catChambre.getId() != null) {
            throw new BadRequestAlertException("A new catChambre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatChambre result = catChambreService.save(catChambre);
        return ResponseEntity.created(new URI("/api/cat-chambres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cat-chambres} : Updates an existing catChambre.
     *
     * @param catChambre the catChambre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catChambre,
     * or with status {@code 400 (Bad Request)} if the catChambre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catChambre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cat-chambres")
    public ResponseEntity<CatChambre> updateCatChambre(@RequestBody CatChambre catChambre) throws URISyntaxException {
        log.debug("REST request to update CatChambre : {}", catChambre);
        if (catChambre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CatChambre result = catChambreService.save(catChambre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catChambre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cat-chambres} : get all the catChambres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of catChambres in body.
     */
    @GetMapping("/cat-chambres")
    public ResponseEntity<List<CatChambre>> getAllCatChambres(Pageable pageable) {
        log.debug("REST request to get a page of CatChambres");
        Page<CatChambre> page = catChambreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cat-chambres/:id} : get the "id" catChambre.
     *
     * @param id the id of the catChambre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catChambre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cat-chambres/{id}")
    public ResponseEntity<CatChambre> getCatChambre(@PathVariable Long id) {
        log.debug("REST request to get CatChambre : {}", id);
        Optional<CatChambre> catChambre = catChambreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catChambre);
    }

    /**
     * {@code DELETE  /cat-chambres/:id} : delete the "id" catChambre.
     *
     * @param id the id of the catChambre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cat-chambres/{id}")
    public ResponseEntity<Void> deleteCatChambre(@PathVariable Long id) {
        log.debug("REST request to delete CatChambre : {}", id);
        catChambreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
