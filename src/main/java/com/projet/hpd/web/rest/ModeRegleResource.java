package com.projet.hpd.web.rest;

import com.projet.hpd.domain.ModeRegle;
import com.projet.hpd.service.ModeRegleService;
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
 * REST controller for managing {@link com.projet.hpd.domain.ModeRegle}.
 */
@RestController
@RequestMapping("/api")
public class ModeRegleResource {

    private final Logger log = LoggerFactory.getLogger(ModeRegleResource.class);

    private static final String ENTITY_NAME = "modeRegle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModeRegleService modeRegleService;

    public ModeRegleResource(ModeRegleService modeRegleService) {
        this.modeRegleService = modeRegleService;
    }

    /**
     * {@code POST  /mode-regles} : Create a new modeRegle.
     *
     * @param modeRegle the modeRegle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modeRegle, or with status {@code 400 (Bad Request)} if the modeRegle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mode-regles")
    public ResponseEntity<ModeRegle> createModeRegle(@RequestBody ModeRegle modeRegle) throws URISyntaxException {
        log.debug("REST request to save ModeRegle : {}", modeRegle);
        if (modeRegle.getId() != null) {
            throw new BadRequestAlertException("A new modeRegle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModeRegle result = modeRegleService.save(modeRegle);
        return ResponseEntity.created(new URI("/api/mode-regles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mode-regles} : Updates an existing modeRegle.
     *
     * @param modeRegle the modeRegle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modeRegle,
     * or with status {@code 400 (Bad Request)} if the modeRegle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modeRegle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mode-regles")
    public ResponseEntity<ModeRegle> updateModeRegle(@RequestBody ModeRegle modeRegle) throws URISyntaxException {
        log.debug("REST request to update ModeRegle : {}", modeRegle);
        if (modeRegle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModeRegle result = modeRegleService.save(modeRegle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modeRegle.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mode-regles} : get all the modeRegles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modeRegles in body.
     */
    @GetMapping("/mode-regles")
    public ResponseEntity<List<ModeRegle>> getAllModeRegles(Pageable pageable) {
        log.debug("REST request to get a page of ModeRegles");
        Page<ModeRegle> page = modeRegleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mode-regles/:id} : get the "id" modeRegle.
     *
     * @param id the id of the modeRegle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modeRegle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mode-regles/{id}")
    public ResponseEntity<ModeRegle> getModeRegle(@PathVariable Long id) {
        log.debug("REST request to get ModeRegle : {}", id);
        Optional<ModeRegle> modeRegle = modeRegleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modeRegle);
    }

    /**
     * {@code DELETE  /mode-regles/:id} : delete the "id" modeRegle.
     *
     * @param id the id of the modeRegle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mode-regles/{id}")
    public ResponseEntity<Void> deleteModeRegle(@PathVariable Long id) {
        log.debug("REST request to delete ModeRegle : {}", id);
        modeRegleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
