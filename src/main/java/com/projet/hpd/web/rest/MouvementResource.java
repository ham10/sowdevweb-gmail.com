package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Mouvement;
import com.projet.hpd.service.MouvementService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Mouvement}.
 */
@RestController
@RequestMapping("/api")
public class MouvementResource {

    private final Logger log = LoggerFactory.getLogger(MouvementResource.class);

    private static final String ENTITY_NAME = "mouvement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MouvementService mouvementService;

    public MouvementResource(MouvementService mouvementService) {
        this.mouvementService = mouvementService;
    }

    /**
     * {@code POST  /mouvements} : Create a new mouvement.
     *
     * @param mouvement the mouvement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mouvement, or with status {@code 400 (Bad Request)} if the mouvement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mouvements")
    public ResponseEntity<Mouvement> createMouvement(@RequestBody Mouvement mouvement) throws URISyntaxException {
        log.debug("REST request to save Mouvement : {}", mouvement);
        if (mouvement.getId() != null) {
            throw new BadRequestAlertException("A new mouvement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mouvement result = mouvementService.save(mouvement);
        return ResponseEntity.created(new URI("/api/mouvements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mouvements} : Updates an existing mouvement.
     *
     * @param mouvement the mouvement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvement,
     * or with status {@code 400 (Bad Request)} if the mouvement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mouvement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mouvements")
    public ResponseEntity<Mouvement> updateMouvement(@RequestBody Mouvement mouvement) throws URISyntaxException {
        log.debug("REST request to update Mouvement : {}", mouvement);
        if (mouvement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mouvement result = mouvementService.save(mouvement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mouvement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mouvements} : get all the mouvements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mouvements in body.
     */
    @GetMapping("/mouvements")
    public ResponseEntity<List<Mouvement>> getAllMouvements(Pageable pageable) {
        log.debug("REST request to get a page of Mouvements");
        Page<Mouvement> page = mouvementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mouvements/:id} : get the "id" mouvement.
     *
     * @param id the id of the mouvement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mouvement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mouvements/{id}")
    public ResponseEntity<Mouvement> getMouvement(@PathVariable Long id) {
        log.debug("REST request to get Mouvement : {}", id);
        Optional<Mouvement> mouvement = mouvementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mouvement);
    }

    /**
     * {@code DELETE  /mouvements/:id} : delete the "id" mouvement.
     *
     * @param id the id of the mouvement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mouvements/{id}")
    public ResponseEntity<Void> deleteMouvement(@PathVariable Long id) {
        log.debug("REST request to delete Mouvement : {}", id);
        mouvementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
