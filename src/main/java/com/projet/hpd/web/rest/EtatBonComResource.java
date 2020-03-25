package com.projet.hpd.web.rest;

import com.projet.hpd.domain.EtatBonCom;
import com.projet.hpd.service.EtatBonComService;
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
 * REST controller for managing {@link com.projet.hpd.domain.EtatBonCom}.
 */
@RestController
@RequestMapping("/api")
public class EtatBonComResource {

    private final Logger log = LoggerFactory.getLogger(EtatBonComResource.class);

    private static final String ENTITY_NAME = "etatBonCom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatBonComService etatBonComService;

    public EtatBonComResource(EtatBonComService etatBonComService) {
        this.etatBonComService = etatBonComService;
    }

    /**
     * {@code POST  /etat-bon-coms} : Create a new etatBonCom.
     *
     * @param etatBonCom the etatBonCom to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatBonCom, or with status {@code 400 (Bad Request)} if the etatBonCom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-bon-coms")
    public ResponseEntity<EtatBonCom> createEtatBonCom(@RequestBody EtatBonCom etatBonCom) throws URISyntaxException {
        log.debug("REST request to save EtatBonCom : {}", etatBonCom);
        if (etatBonCom.getId() != null) {
            throw new BadRequestAlertException("A new etatBonCom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatBonCom result = etatBonComService.save(etatBonCom);
        return ResponseEntity.created(new URI("/api/etat-bon-coms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-bon-coms} : Updates an existing etatBonCom.
     *
     * @param etatBonCom the etatBonCom to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatBonCom,
     * or with status {@code 400 (Bad Request)} if the etatBonCom is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatBonCom couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-bon-coms")
    public ResponseEntity<EtatBonCom> updateEtatBonCom(@RequestBody EtatBonCom etatBonCom) throws URISyntaxException {
        log.debug("REST request to update EtatBonCom : {}", etatBonCom);
        if (etatBonCom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatBonCom result = etatBonComService.save(etatBonCom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatBonCom.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-bon-coms} : get all the etatBonComs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatBonComs in body.
     */
    @GetMapping("/etat-bon-coms")
    public ResponseEntity<List<EtatBonCom>> getAllEtatBonComs(Pageable pageable) {
        log.debug("REST request to get a page of EtatBonComs");
        Page<EtatBonCom> page = etatBonComService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-bon-coms/:id} : get the "id" etatBonCom.
     *
     * @param id the id of the etatBonCom to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatBonCom, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-bon-coms/{id}")
    public ResponseEntity<EtatBonCom> getEtatBonCom(@PathVariable Long id) {
        log.debug("REST request to get EtatBonCom : {}", id);
        Optional<EtatBonCom> etatBonCom = etatBonComService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatBonCom);
    }

    /**
     * {@code DELETE  /etat-bon-coms/:id} : delete the "id" etatBonCom.
     *
     * @param id the id of the etatBonCom to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-bon-coms/{id}")
    public ResponseEntity<Void> deleteEtatBonCom(@PathVariable Long id) {
        log.debug("REST request to delete EtatBonCom : {}", id);
        etatBonComService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
