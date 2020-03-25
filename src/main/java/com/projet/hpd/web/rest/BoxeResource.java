package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Boxe;
import com.projet.hpd.service.BoxeService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Boxe}.
 */
@RestController
@RequestMapping("/api")
public class BoxeResource {

    private final Logger log = LoggerFactory.getLogger(BoxeResource.class);

    private static final String ENTITY_NAME = "boxe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoxeService boxeService;

    public BoxeResource(BoxeService boxeService) {
        this.boxeService = boxeService;
    }

    /**
     * {@code POST  /boxes} : Create a new boxe.
     *
     * @param boxe the boxe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boxe, or with status {@code 400 (Bad Request)} if the boxe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boxes")
    public ResponseEntity<Boxe> createBoxe(@RequestBody Boxe boxe) throws URISyntaxException {
        log.debug("REST request to save Boxe : {}", boxe);
        if (boxe.getId() != null) {
            throw new BadRequestAlertException("A new boxe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Boxe result = boxeService.save(boxe);
        return ResponseEntity.created(new URI("/api/boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boxes} : Updates an existing boxe.
     *
     * @param boxe the boxe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boxe,
     * or with status {@code 400 (Bad Request)} if the boxe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boxe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boxes")
    public ResponseEntity<Boxe> updateBoxe(@RequestBody Boxe boxe) throws URISyntaxException {
        log.debug("REST request to update Boxe : {}", boxe);
        if (boxe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Boxe result = boxeService.save(boxe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boxe.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boxes} : get all the boxes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boxes in body.
     */
    @GetMapping("/boxes")
    public ResponseEntity<List<Boxe>> getAllBoxes(Pageable pageable) {
        log.debug("REST request to get a page of Boxes");
        Page<Boxe> page = boxeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boxes/:id} : get the "id" boxe.
     *
     * @param id the id of the boxe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boxe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boxes/{id}")
    public ResponseEntity<Boxe> getBoxe(@PathVariable Long id) {
        log.debug("REST request to get Boxe : {}", id);
        Optional<Boxe> boxe = boxeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boxe);
    }

    /**
     * {@code DELETE  /boxes/:id} : delete the "id" boxe.
     *
     * @param id the id of the boxe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boxes/{id}")
    public ResponseEntity<Void> deleteBoxe(@PathVariable Long id) {
        log.debug("REST request to delete Boxe : {}", id);
        boxeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
