package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Chambre;
import com.projet.hpd.service.ChambreService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Chambre}.
 */
@RestController
@RequestMapping("/api")
public class ChambreResource {

    private final Logger log = LoggerFactory.getLogger(ChambreResource.class);

    private static final String ENTITY_NAME = "chambre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChambreService chambreService;

    public ChambreResource(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    /**
     * {@code POST  /chambres} : Create a new chambre.
     *
     * @param chambre the chambre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chambre, or with status {@code 400 (Bad Request)} if the chambre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chambres")
    public ResponseEntity<Chambre> createChambre(@RequestBody Chambre chambre) throws URISyntaxException {
        log.debug("REST request to save Chambre : {}", chambre);
        if (chambre.getId() != null) {
            throw new BadRequestAlertException("A new chambre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chambre result = chambreService.save(chambre);
        return ResponseEntity.created(new URI("/api/chambres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chambres} : Updates an existing chambre.
     *
     * @param chambre the chambre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chambre,
     * or with status {@code 400 (Bad Request)} if the chambre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chambre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chambres")
    public ResponseEntity<Chambre> updateChambre(@RequestBody Chambre chambre) throws URISyntaxException {
        log.debug("REST request to update Chambre : {}", chambre);
        if (chambre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chambre result = chambreService.save(chambre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chambre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chambres} : get all the chambres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chambres in body.
     */
    @GetMapping("/chambres")
    public ResponseEntity<List<Chambre>> getAllChambres(Pageable pageable) {
        log.debug("REST request to get a page of Chambres");
        Page<Chambre> page = chambreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /chambres/:id} : get the "id" chambre.
     *
     * @param id the id of the chambre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chambre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chambres/{id}")
    public ResponseEntity<Chambre> getChambre(@PathVariable Long id) {
        log.debug("REST request to get Chambre : {}", id);
        Optional<Chambre> chambre = chambreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chambre);
    }

    /**
     * {@code DELETE  /chambres/:id} : delete the "id" chambre.
     *
     * @param id the id of the chambre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chambres/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        log.debug("REST request to delete Chambre : {}", id);
        chambreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
