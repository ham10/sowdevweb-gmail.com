package com.projet.hpd.web.rest;

import com.projet.hpd.domain.CompteGene;
import com.projet.hpd.service.CompteGeneService;
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
 * REST controller for managing {@link com.projet.hpd.domain.CompteGene}.
 */
@RestController
@RequestMapping("/api")
public class CompteGeneResource {

    private final Logger log = LoggerFactory.getLogger(CompteGeneResource.class);

    private static final String ENTITY_NAME = "compteGene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompteGeneService compteGeneService;

    public CompteGeneResource(CompteGeneService compteGeneService) {
        this.compteGeneService = compteGeneService;
    }

    /**
     * {@code POST  /compte-genes} : Create a new compteGene.
     *
     * @param compteGene the compteGene to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compteGene, or with status {@code 400 (Bad Request)} if the compteGene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compte-genes")
    public ResponseEntity<CompteGene> createCompteGene(@RequestBody CompteGene compteGene) throws URISyntaxException {
        log.debug("REST request to save CompteGene : {}", compteGene);
        if (compteGene.getId() != null) {
            throw new BadRequestAlertException("A new compteGene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompteGene result = compteGeneService.save(compteGene);
        return ResponseEntity.created(new URI("/api/compte-genes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compte-genes} : Updates an existing compteGene.
     *
     * @param compteGene the compteGene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compteGene,
     * or with status {@code 400 (Bad Request)} if the compteGene is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compteGene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compte-genes")
    public ResponseEntity<CompteGene> updateCompteGene(@RequestBody CompteGene compteGene) throws URISyntaxException {
        log.debug("REST request to update CompteGene : {}", compteGene);
        if (compteGene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompteGene result = compteGeneService.save(compteGene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compteGene.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compte-genes} : get all the compteGenes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compteGenes in body.
     */
    @GetMapping("/compte-genes")
    public ResponseEntity<List<CompteGene>> getAllCompteGenes(Pageable pageable) {
        log.debug("REST request to get a page of CompteGenes");
        Page<CompteGene> page = compteGeneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compte-genes/:id} : get the "id" compteGene.
     *
     * @param id the id of the compteGene to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compteGene, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compte-genes/{id}")
    public ResponseEntity<CompteGene> getCompteGene(@PathVariable Long id) {
        log.debug("REST request to get CompteGene : {}", id);
        Optional<CompteGene> compteGene = compteGeneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteGene);
    }

    /**
     * {@code DELETE  /compte-genes/:id} : delete the "id" compteGene.
     *
     * @param id the id of the compteGene to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compte-genes/{id}")
    public ResponseEntity<Void> deleteCompteGene(@PathVariable Long id) {
        log.debug("REST request to delete CompteGene : {}", id);
        compteGeneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
