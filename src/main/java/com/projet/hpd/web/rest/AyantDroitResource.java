package com.projet.hpd.web.rest;

import com.projet.hpd.domain.AyantDroit;
import com.projet.hpd.service.AyantDroitService;
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
 * REST controller for managing {@link com.projet.hpd.domain.AyantDroit}.
 */
@RestController
@RequestMapping("/api")
public class AyantDroitResource {

    private final Logger log = LoggerFactory.getLogger(AyantDroitResource.class);

    private static final String ENTITY_NAME = "ayantDroit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AyantDroitService ayantDroitService;

    public AyantDroitResource(AyantDroitService ayantDroitService) {
        this.ayantDroitService = ayantDroitService;
    }

    /**
     * {@code POST  /ayant-droits} : Create a new ayantDroit.
     *
     * @param ayantDroit the ayantDroit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ayantDroit, or with status {@code 400 (Bad Request)} if the ayantDroit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ayant-droits")
    public ResponseEntity<AyantDroit> createAyantDroit(@RequestBody AyantDroit ayantDroit) throws URISyntaxException {
        log.debug("REST request to save AyantDroit : {}", ayantDroit);
        if (ayantDroit.getId() != null) {
            throw new BadRequestAlertException("A new ayantDroit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AyantDroit result = ayantDroitService.save(ayantDroit);
        return ResponseEntity.created(new URI("/api/ayant-droits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ayant-droits} : Updates an existing ayantDroit.
     *
     * @param ayantDroit the ayantDroit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ayantDroit,
     * or with status {@code 400 (Bad Request)} if the ayantDroit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ayantDroit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ayant-droits")
    public ResponseEntity<AyantDroit> updateAyantDroit(@RequestBody AyantDroit ayantDroit) throws URISyntaxException {
        log.debug("REST request to update AyantDroit : {}", ayantDroit);
        if (ayantDroit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AyantDroit result = ayantDroitService.save(ayantDroit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ayantDroit.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ayant-droits} : get all the ayantDroits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ayantDroits in body.
     */
    @GetMapping("/ayant-droits")
    public ResponseEntity<List<AyantDroit>> getAllAyantDroits(Pageable pageable) {
        log.debug("REST request to get a page of AyantDroits");
        Page<AyantDroit> page = ayantDroitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ayant-droits/:id} : get the "id" ayantDroit.
     *
     * @param id the id of the ayantDroit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ayantDroit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ayant-droits/{id}")
    public ResponseEntity<AyantDroit> getAyantDroit(@PathVariable Long id) {
        log.debug("REST request to get AyantDroit : {}", id);
        Optional<AyantDroit> ayantDroit = ayantDroitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ayantDroit);
    }

    /**
     * {@code DELETE  /ayant-droits/:id} : delete the "id" ayantDroit.
     *
     * @param id the id of the ayantDroit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ayant-droits/{id}")
    public ResponseEntity<Void> deleteAyantDroit(@PathVariable Long id) {
        log.debug("REST request to delete AyantDroit : {}", id);
        ayantDroitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
