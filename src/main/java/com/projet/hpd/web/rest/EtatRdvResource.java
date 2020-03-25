package com.projet.hpd.web.rest;

import com.projet.hpd.domain.EtatRdv;
import com.projet.hpd.service.EtatRdvService;
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
 * REST controller for managing {@link com.projet.hpd.domain.EtatRdv}.
 */
@RestController
@RequestMapping("/api")
public class EtatRdvResource {

    private final Logger log = LoggerFactory.getLogger(EtatRdvResource.class);

    private static final String ENTITY_NAME = "etatRdv";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtatRdvService etatRdvService;

    public EtatRdvResource(EtatRdvService etatRdvService) {
        this.etatRdvService = etatRdvService;
    }

    /**
     * {@code POST  /etat-rdvs} : Create a new etatRdv.
     *
     * @param etatRdv the etatRdv to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etatRdv, or with status {@code 400 (Bad Request)} if the etatRdv has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etat-rdvs")
    public ResponseEntity<EtatRdv> createEtatRdv(@RequestBody EtatRdv etatRdv) throws URISyntaxException {
        log.debug("REST request to save EtatRdv : {}", etatRdv);
        if (etatRdv.getId() != null) {
            throw new BadRequestAlertException("A new etatRdv cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatRdv result = etatRdvService.save(etatRdv);
        return ResponseEntity.created(new URI("/api/etat-rdvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etat-rdvs} : Updates an existing etatRdv.
     *
     * @param etatRdv the etatRdv to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etatRdv,
     * or with status {@code 400 (Bad Request)} if the etatRdv is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etatRdv couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etat-rdvs")
    public ResponseEntity<EtatRdv> updateEtatRdv(@RequestBody EtatRdv etatRdv) throws URISyntaxException {
        log.debug("REST request to update EtatRdv : {}", etatRdv);
        if (etatRdv.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatRdv result = etatRdvService.save(etatRdv);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etatRdv.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etat-rdvs} : get all the etatRdvs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etatRdvs in body.
     */
    @GetMapping("/etat-rdvs")
    public ResponseEntity<List<EtatRdv>> getAllEtatRdvs(Pageable pageable) {
        log.debug("REST request to get a page of EtatRdvs");
        Page<EtatRdv> page = etatRdvService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etat-rdvs/:id} : get the "id" etatRdv.
     *
     * @param id the id of the etatRdv to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etatRdv, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etat-rdvs/{id}")
    public ResponseEntity<EtatRdv> getEtatRdv(@PathVariable Long id) {
        log.debug("REST request to get EtatRdv : {}", id);
        Optional<EtatRdv> etatRdv = etatRdvService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatRdv);
    }

    /**
     * {@code DELETE  /etat-rdvs/:id} : delete the "id" etatRdv.
     *
     * @param id the id of the etatRdv to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etat-rdvs/{id}")
    public ResponseEntity<Void> deleteEtatRdv(@PathVariable Long id) {
        log.debug("REST request to delete EtatRdv : {}", id);
        etatRdvService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
