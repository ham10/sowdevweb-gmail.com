package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Vaccin;
import com.projet.hpd.service.VaccinService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Vaccin}.
 */
@RestController
@RequestMapping("/api")
public class VaccinResource {

    private final Logger log = LoggerFactory.getLogger(VaccinResource.class);

    private static final String ENTITY_NAME = "vaccin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VaccinService vaccinService;

    public VaccinResource(VaccinService vaccinService) {
        this.vaccinService = vaccinService;
    }

    /**
     * {@code POST  /vaccins} : Create a new vaccin.
     *
     * @param vaccin the vaccin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vaccin, or with status {@code 400 (Bad Request)} if the vaccin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vaccins")
    public ResponseEntity<Vaccin> createVaccin(@RequestBody Vaccin vaccin) throws URISyntaxException {
        log.debug("REST request to save Vaccin : {}", vaccin);
        if (vaccin.getId() != null) {
            throw new BadRequestAlertException("A new vaccin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vaccin result = vaccinService.save(vaccin);
        return ResponseEntity.created(new URI("/api/vaccins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vaccins} : Updates an existing vaccin.
     *
     * @param vaccin the vaccin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vaccin,
     * or with status {@code 400 (Bad Request)} if the vaccin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vaccin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vaccins")
    public ResponseEntity<Vaccin> updateVaccin(@RequestBody Vaccin vaccin) throws URISyntaxException {
        log.debug("REST request to update Vaccin : {}", vaccin);
        if (vaccin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vaccin result = vaccinService.save(vaccin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vaccin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vaccins} : get all the vaccins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vaccins in body.
     */
    @GetMapping("/vaccins")
    public ResponseEntity<List<Vaccin>> getAllVaccins(Pageable pageable) {
        log.debug("REST request to get a page of Vaccins");
        Page<Vaccin> page = vaccinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vaccins/:id} : get the "id" vaccin.
     *
     * @param id the id of the vaccin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vaccin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vaccins/{id}")
    public ResponseEntity<Vaccin> getVaccin(@PathVariable Long id) {
        log.debug("REST request to get Vaccin : {}", id);
        Optional<Vaccin> vaccin = vaccinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vaccin);
    }

    /**
     * {@code DELETE  /vaccins/:id} : delete the "id" vaccin.
     *
     * @param id the id of the vaccin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vaccins/{id}")
    public ResponseEntity<Void> deleteVaccin(@PathVariable Long id) {
        log.debug("REST request to delete Vaccin : {}", id);
        vaccinService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
