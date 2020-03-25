package com.projet.hpd.web.rest;

import com.projet.hpd.domain.LigneLivraison;
import com.projet.hpd.service.LigneLivraisonService;
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
 * REST controller for managing {@link com.projet.hpd.domain.LigneLivraison}.
 */
@RestController
@RequestMapping("/api")
public class LigneLivraisonResource {

    private final Logger log = LoggerFactory.getLogger(LigneLivraisonResource.class);

    private static final String ENTITY_NAME = "ligneLivraison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneLivraisonService ligneLivraisonService;

    public LigneLivraisonResource(LigneLivraisonService ligneLivraisonService) {
        this.ligneLivraisonService = ligneLivraisonService;
    }

    /**
     * {@code POST  /ligne-livraisons} : Create a new ligneLivraison.
     *
     * @param ligneLivraison the ligneLivraison to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneLivraison, or with status {@code 400 (Bad Request)} if the ligneLivraison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-livraisons")
    public ResponseEntity<LigneLivraison> createLigneLivraison(@RequestBody LigneLivraison ligneLivraison) throws URISyntaxException {
        log.debug("REST request to save LigneLivraison : {}", ligneLivraison);
        if (ligneLivraison.getId() != null) {
            throw new BadRequestAlertException("A new ligneLivraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneLivraison result = ligneLivraisonService.save(ligneLivraison);
        return ResponseEntity.created(new URI("/api/ligne-livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-livraisons} : Updates an existing ligneLivraison.
     *
     * @param ligneLivraison the ligneLivraison to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneLivraison,
     * or with status {@code 400 (Bad Request)} if the ligneLivraison is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneLivraison couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-livraisons")
    public ResponseEntity<LigneLivraison> updateLigneLivraison(@RequestBody LigneLivraison ligneLivraison) throws URISyntaxException {
        log.debug("REST request to update LigneLivraison : {}", ligneLivraison);
        if (ligneLivraison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LigneLivraison result = ligneLivraisonService.save(ligneLivraison);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneLivraison.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ligne-livraisons} : get all the ligneLivraisons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneLivraisons in body.
     */
    @GetMapping("/ligne-livraisons")
    public ResponseEntity<List<LigneLivraison>> getAllLigneLivraisons(Pageable pageable) {
        log.debug("REST request to get a page of LigneLivraisons");
        Page<LigneLivraison> page = ligneLivraisonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ligne-livraisons/:id} : get the "id" ligneLivraison.
     *
     * @param id the id of the ligneLivraison to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneLivraison, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-livraisons/{id}")
    public ResponseEntity<LigneLivraison> getLigneLivraison(@PathVariable Long id) {
        log.debug("REST request to get LigneLivraison : {}", id);
        Optional<LigneLivraison> ligneLivraison = ligneLivraisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneLivraison);
    }

    /**
     * {@code DELETE  /ligne-livraisons/:id} : delete the "id" ligneLivraison.
     *
     * @param id the id of the ligneLivraison to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-livraisons/{id}")
    public ResponseEntity<Void> deleteLigneLivraison(@PathVariable Long id) {
        log.debug("REST request to delete LigneLivraison : {}", id);
        ligneLivraisonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
