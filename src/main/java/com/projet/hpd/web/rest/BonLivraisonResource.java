package com.projet.hpd.web.rest;

import com.projet.hpd.domain.BonLivraison;
import com.projet.hpd.service.BonLivraisonService;
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
 * REST controller for managing {@link com.projet.hpd.domain.BonLivraison}.
 */
@RestController
@RequestMapping("/api")
public class BonLivraisonResource {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonResource.class);

    private static final String ENTITY_NAME = "bonLivraison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonLivraisonService bonLivraisonService;

    public BonLivraisonResource(BonLivraisonService bonLivraisonService) {
        this.bonLivraisonService = bonLivraisonService;
    }

    /**
     * {@code POST  /bon-livraisons} : Create a new bonLivraison.
     *
     * @param bonLivraison the bonLivraison to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonLivraison, or with status {@code 400 (Bad Request)} if the bonLivraison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bon-livraisons")
    public ResponseEntity<BonLivraison> createBonLivraison(@RequestBody BonLivraison bonLivraison) throws URISyntaxException {
        log.debug("REST request to save BonLivraison : {}", bonLivraison);
        if (bonLivraison.getId() != null) {
            throw new BadRequestAlertException("A new bonLivraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonLivraison result = bonLivraisonService.save(bonLivraison);
        return ResponseEntity.created(new URI("/api/bon-livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-livraisons} : Updates an existing bonLivraison.
     *
     * @param bonLivraison the bonLivraison to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonLivraison,
     * or with status {@code 400 (Bad Request)} if the bonLivraison is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonLivraison couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bon-livraisons")
    public ResponseEntity<BonLivraison> updateBonLivraison(@RequestBody BonLivraison bonLivraison) throws URISyntaxException {
        log.debug("REST request to update BonLivraison : {}", bonLivraison);
        if (bonLivraison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BonLivraison result = bonLivraisonService.save(bonLivraison);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonLivraison.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bon-livraisons} : get all the bonLivraisons.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonLivraisons in body.
     */
    @GetMapping("/bon-livraisons")
    public ResponseEntity<List<BonLivraison>> getAllBonLivraisons(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("bondecommande-is-null".equals(filter)) {
            log.debug("REST request to get all BonLivraisons where bonDeCommande is null");
            return new ResponseEntity<>(bonLivraisonService.findAllWhereBonDeCommandeIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of BonLivraisons");
        Page<BonLivraison> page = bonLivraisonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bon-livraisons/:id} : get the "id" bonLivraison.
     *
     * @param id the id of the bonLivraison to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonLivraison, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bon-livraisons/{id}")
    public ResponseEntity<BonLivraison> getBonLivraison(@PathVariable Long id) {
        log.debug("REST request to get BonLivraison : {}", id);
        Optional<BonLivraison> bonLivraison = bonLivraisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonLivraison);
    }

    /**
     * {@code DELETE  /bon-livraisons/:id} : delete the "id" bonLivraison.
     *
     * @param id the id of the bonLivraison to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bon-livraisons/{id}")
    public ResponseEntity<Void> deleteBonLivraison(@PathVariable Long id) {
        log.debug("REST request to delete BonLivraison : {}", id);
        bonLivraisonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
