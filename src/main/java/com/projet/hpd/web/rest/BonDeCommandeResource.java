package com.projet.hpd.web.rest;

import com.projet.hpd.domain.BonDeCommande;
import com.projet.hpd.service.BonDeCommandeService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.projet.hpd.domain.BonDeCommande}.
 */
@RestController
@RequestMapping("/api")
public class BonDeCommandeResource {

    private final Logger log = LoggerFactory.getLogger(BonDeCommandeResource.class);

    private static final String ENTITY_NAME = "bonDeCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonDeCommandeService bonDeCommandeService;

    public BonDeCommandeResource(BonDeCommandeService bonDeCommandeService) {
        this.bonDeCommandeService = bonDeCommandeService;
    }

    /**
     * {@code POST  /bon-de-commandes} : Create a new bonDeCommande.
     *
     * @param bonDeCommande the bonDeCommande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonDeCommande, or with status {@code 400 (Bad Request)} if the bonDeCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bon-de-commandes")
    public ResponseEntity<BonDeCommande> createBonDeCommande(@RequestBody BonDeCommande bonDeCommande) throws URISyntaxException {
        log.debug("REST request to save BonDeCommande : {}", bonDeCommande);
        if (bonDeCommande.getId() != null) {
            throw new BadRequestAlertException("A new bonDeCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonDeCommande result = bonDeCommandeService.save(bonDeCommande);
        return ResponseEntity.created(new URI("/api/bon-de-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-de-commandes} : Updates an existing bonDeCommande.
     *
     * @param bonDeCommande the bonDeCommande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonDeCommande,
     * or with status {@code 400 (Bad Request)} if the bonDeCommande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonDeCommande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bon-de-commandes")
    public ResponseEntity<BonDeCommande> updateBonDeCommande(@RequestBody BonDeCommande bonDeCommande) throws URISyntaxException {
        log.debug("REST request to update BonDeCommande : {}", bonDeCommande);
        if (bonDeCommande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BonDeCommande result = bonDeCommandeService.save(bonDeCommande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonDeCommande.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bon-de-commandes} : get all the bonDeCommandes.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonDeCommandes in body.
     */
    @GetMapping("/bon-de-commandes")
    public ResponseEntity<List<BonDeCommande>> getAllBonDeCommandes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("offre-is-null".equals(filter)) {
            log.debug("REST request to get all BonDeCommandes where offre is null");
            return new ResponseEntity<>(bonDeCommandeService.findAllWhereOffreIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of BonDeCommandes");
        Page<BonDeCommande> page = bonDeCommandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bon-de-commandes/:id} : get the "id" bonDeCommande.
     *
     * @param id the id of the bonDeCommande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonDeCommande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bon-de-commandes/{id}")
    public ResponseEntity<BonDeCommande> getBonDeCommande(@PathVariable Long id) {
        log.debug("REST request to get BonDeCommande : {}", id);
        Optional<BonDeCommande> bonDeCommande = bonDeCommandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonDeCommande);
    }

    /**
     * {@code DELETE  /bon-de-commandes/:id} : delete the "id" bonDeCommande.
     *
     * @param id the id of the bonDeCommande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bon-de-commandes/{id}")
    public ResponseEntity<Void> deleteBonDeCommande(@PathVariable Long id) {
        log.debug("REST request to delete BonDeCommande : {}", id);
        bonDeCommandeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
