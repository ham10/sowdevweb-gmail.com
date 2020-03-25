package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Hospitalisation;
import com.projet.hpd.service.HospitalisationService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Hospitalisation}.
 */
@RestController
@RequestMapping("/api")
public class HospitalisationResource {

    private final Logger log = LoggerFactory.getLogger(HospitalisationResource.class);

    private static final String ENTITY_NAME = "hospitalisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospitalisationService hospitalisationService;

    public HospitalisationResource(HospitalisationService hospitalisationService) {
        this.hospitalisationService = hospitalisationService;
    }

    /**
     * {@code POST  /hospitalisations} : Create a new hospitalisation.
     *
     * @param hospitalisation the hospitalisation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalisation, or with status {@code 400 (Bad Request)} if the hospitalisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospitalisations")
    public ResponseEntity<Hospitalisation> createHospitalisation(@RequestBody Hospitalisation hospitalisation) throws URISyntaxException {
        log.debug("REST request to save Hospitalisation : {}", hospitalisation);
        if (hospitalisation.getId() != null) {
            throw new BadRequestAlertException("A new hospitalisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hospitalisation result = hospitalisationService.save(hospitalisation);
        return ResponseEntity.created(new URI("/api/hospitalisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospitalisations} : Updates an existing hospitalisation.
     *
     * @param hospitalisation the hospitalisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalisation,
     * or with status {@code 400 (Bad Request)} if the hospitalisation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospitalisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospitalisations")
    public ResponseEntity<Hospitalisation> updateHospitalisation(@RequestBody Hospitalisation hospitalisation) throws URISyntaxException {
        log.debug("REST request to update Hospitalisation : {}", hospitalisation);
        if (hospitalisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hospitalisation result = hospitalisationService.save(hospitalisation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hospitalisation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hospitalisations} : get all the hospitalisations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitalisations in body.
     */
    @GetMapping("/hospitalisations")
    public ResponseEntity<List<Hospitalisation>> getAllHospitalisations(Pageable pageable) {
        log.debug("REST request to get a page of Hospitalisations");
        Page<Hospitalisation> page = hospitalisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hospitalisations/:id} : get the "id" hospitalisation.
     *
     * @param id the id of the hospitalisation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospitalisation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospitalisations/{id}")
    public ResponseEntity<Hospitalisation> getHospitalisation(@PathVariable Long id) {
        log.debug("REST request to get Hospitalisation : {}", id);
        Optional<Hospitalisation> hospitalisation = hospitalisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hospitalisation);
    }

    /**
     * {@code DELETE  /hospitalisations/:id} : delete the "id" hospitalisation.
     *
     * @param id the id of the hospitalisation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospitalisations/{id}")
    public ResponseEntity<Void> deleteHospitalisation(@PathVariable Long id) {
        log.debug("REST request to delete Hospitalisation : {}", id);
        hospitalisationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
