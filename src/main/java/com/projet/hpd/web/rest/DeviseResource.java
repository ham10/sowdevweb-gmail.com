package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Devise;
import com.projet.hpd.service.DeviseService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Devise}.
 */
@RestController
@RequestMapping("/api")
public class DeviseResource {

    private final Logger log = LoggerFactory.getLogger(DeviseResource.class);

    private static final String ENTITY_NAME = "devise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviseService deviseService;

    public DeviseResource(DeviseService deviseService) {
        this.deviseService = deviseService;
    }

    /**
     * {@code POST  /devises} : Create a new devise.
     *
     * @param devise the devise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new devise, or with status {@code 400 (Bad Request)} if the devise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devises")
    public ResponseEntity<Devise> createDevise(@RequestBody Devise devise) throws URISyntaxException {
        log.debug("REST request to save Devise : {}", devise);
        if (devise.getId() != null) {
            throw new BadRequestAlertException("A new devise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Devise result = deviseService.save(devise);
        return ResponseEntity.created(new URI("/api/devises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devises} : Updates an existing devise.
     *
     * @param devise the devise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devise,
     * or with status {@code 400 (Bad Request)} if the devise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the devise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devises")
    public ResponseEntity<Devise> updateDevise(@RequestBody Devise devise) throws URISyntaxException {
        log.debug("REST request to update Devise : {}", devise);
        if (devise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Devise result = deviseService.save(devise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devise.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /devises} : get all the devises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devises in body.
     */
    @GetMapping("/devises")
    public ResponseEntity<List<Devise>> getAllDevises(Pageable pageable) {
        log.debug("REST request to get a page of Devises");
        Page<Devise> page = deviseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /devises/:id} : get the "id" devise.
     *
     * @param id the id of the devise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the devise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devises/{id}")
    public ResponseEntity<Devise> getDevise(@PathVariable Long id) {
        log.debug("REST request to get Devise : {}", id);
        Optional<Devise> devise = deviseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devise);
    }

    /**
     * {@code DELETE  /devises/:id} : delete the "id" devise.
     *
     * @param id the id of the devise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devises/{id}")
    public ResponseEntity<Void> deleteDevise(@PathVariable Long id) {
        log.debug("REST request to delete Devise : {}", id);
        deviseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
