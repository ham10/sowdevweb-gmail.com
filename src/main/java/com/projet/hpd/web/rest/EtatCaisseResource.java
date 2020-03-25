package com.projet.hpd.web.rest;

import com.projet.hpd.domain.EtatCaisse;
import com.projet.hpd.service.EtatCaisseService;
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
 * REST controller for managing {@link com.projet.hpd.domain.EtatCaisse}.
 */
@RestController
@RequestMapping("/api")
public class EtatCaisseResource {

    private final Logger log = LoggerFactory.getLogger(EtatCaisseResource.class);

    private static final String ENTITY_NAME = "etatCaisse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatCaisseService etatCaisseService;

    public EtatCaisseResource(EtatCaisseService etatCaisseService) {
        this.etatCaisseService = etatCaisseService;
    }

    /**
     * {@code POST  /etat-caisses} : Create a new etatCaisse.
     *
     * @param etatCaisse the etatCaisse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatCaisse, or with status {@code 400 (Bad Request)} if the etatCaisse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-caisses")
    public ResponseEntity<EtatCaisse> createEtatCaisse(@RequestBody EtatCaisse etatCaisse) throws URISyntaxException {
        log.debug("REST request to save EtatCaisse : {}", etatCaisse);
        if (etatCaisse.getId() != null) {
            throw new BadRequestAlertException("A new etatCaisse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatCaisse result = etatCaisseService.save(etatCaisse);
        return ResponseEntity.created(new URI("/api/etat-caisses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-caisses} : Updates an existing etatCaisse.
     *
     * @param etatCaisse the etatCaisse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatCaisse,
     * or with status {@code 400 (Bad Request)} if the etatCaisse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatCaisse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-caisses")
    public ResponseEntity<EtatCaisse> updateEtatCaisse(@RequestBody EtatCaisse etatCaisse) throws URISyntaxException {
        log.debug("REST request to update EtatCaisse : {}", etatCaisse);
        if (etatCaisse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatCaisse result = etatCaisseService.save(etatCaisse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatCaisse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-caisses} : get all the etatCaisses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatCaisses in body.
     */
    @GetMapping("/etat-caisses")
    public ResponseEntity<List<EtatCaisse>> getAllEtatCaisses(Pageable pageable) {
        log.debug("REST request to get a page of EtatCaisses");
        Page<EtatCaisse> page = etatCaisseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-caisses/:id} : get the "id" etatCaisse.
     *
     * @param id the id of the etatCaisse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatCaisse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-caisses/{id}")
    public ResponseEntity<EtatCaisse> getEtatCaisse(@PathVariable Long id) {
        log.debug("REST request to get EtatCaisse : {}", id);
        Optional<EtatCaisse> etatCaisse = etatCaisseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatCaisse);
    }

    /**
     * {@code DELETE  /etat-caisses/:id} : delete the "id" etatCaisse.
     *
     * @param id the id of the etatCaisse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-caisses/{id}")
    public ResponseEntity<Void> deleteEtatCaisse(@PathVariable Long id) {
        log.debug("REST request to delete EtatCaisse : {}", id);
        etatCaisseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
