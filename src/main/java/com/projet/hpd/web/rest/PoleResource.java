package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Pole;
import com.projet.hpd.service.PoleService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Pole}.
 */
@RestController
@RequestMapping("/api")
public class PoleResource {

    private final Logger log = LoggerFactory.getLogger(PoleResource.class);

    private static final String ENTITY_NAME = "pole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoleService poleService;

    public PoleResource(PoleService poleService) {
        this.poleService = poleService;
    }

    /**
     * {@code POST  /poles} : Create a new pole.
     *
     * @param pole the pole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pole, or with status {@code 400 (Bad Request)} if the pole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poles")
    public ResponseEntity<Pole> createPole(@RequestBody Pole pole) throws URISyntaxException {
        log.debug("REST request to save Pole : {}", pole);
        if (pole.getId() != null) {
            throw new BadRequestAlertException("A new pole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pole result = poleService.save(pole);
        return ResponseEntity.created(new URI("/api/poles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poles} : Updates an existing pole.
     *
     * @param pole the pole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pole,
     * or with status {@code 400 (Bad Request)} if the pole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poles")
    public ResponseEntity<Pole> updatePole(@RequestBody Pole pole) throws URISyntaxException {
        log.debug("REST request to update Pole : {}", pole);
        if (pole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pole result = poleService.save(pole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poles} : get all the poles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poles in body.
     */
    @GetMapping("/poles")
    public ResponseEntity<List<Pole>> getAllPoles(Pageable pageable) {
        log.debug("REST request to get a page of Poles");
        Page<Pole> page = poleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /poles/:id} : get the "id" pole.
     *
     * @param id the id of the pole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poles/{id}")
    public ResponseEntity<Pole> getPole(@PathVariable Long id) {
        log.debug("REST request to get Pole : {}", id);
        Optional<Pole> pole = poleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pole);
    }

    /**
     * {@code DELETE  /poles/:id} : delete the "id" pole.
     *
     * @param id the id of the pole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poles/{id}")
    public ResponseEntity<Void> deletePole(@PathVariable Long id) {
        log.debug("REST request to delete Pole : {}", id);
        poleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
