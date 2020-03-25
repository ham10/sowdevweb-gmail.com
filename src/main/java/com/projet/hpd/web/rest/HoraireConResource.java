package com.projet.hpd.web.rest;

import com.projet.hpd.domain.HoraireCon;
import com.projet.hpd.service.HoraireConService;
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
 * REST controller for managing {@link com.projet.hpd.domain.HoraireCon}.
 */
@RestController
@RequestMapping("/api")
public class HoraireConResource {

    private final Logger log = LoggerFactory.getLogger(HoraireConResource.class);

    private static final String ENTITY_NAME = "horaireCon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoraireConService horaireConService;

    public HoraireConResource(HoraireConService horaireConService) {
        this.horaireConService = horaireConService;
    }

    /**
     * {@code POST  /horaire-cons} : Create a new horaireCon.
     *
     * @param horaireCon the horaireCon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horaireCon, or with status {@code 400 (Bad Request)} if the horaireCon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horaire-cons")
    public ResponseEntity<HoraireCon> createHoraireCon(@RequestBody HoraireCon horaireCon) throws URISyntaxException {
        log.debug("REST request to save HoraireCon : {}", horaireCon);
        if (horaireCon.getId() != null) {
            throw new BadRequestAlertException("A new horaireCon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoraireCon result = horaireConService.save(horaireCon);
        return ResponseEntity.created(new URI("/api/horaire-cons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horaire-cons} : Updates an existing horaireCon.
     *
     * @param horaireCon the horaireCon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horaireCon,
     * or with status {@code 400 (Bad Request)} if the horaireCon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horaireCon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horaire-cons")
    public ResponseEntity<HoraireCon> updateHoraireCon(@RequestBody HoraireCon horaireCon) throws URISyntaxException {
        log.debug("REST request to update HoraireCon : {}", horaireCon);
        if (horaireCon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HoraireCon result = horaireConService.save(horaireCon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, horaireCon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horaire-cons} : get all the horaireCons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horaireCons in body.
     */
    @GetMapping("/horaire-cons")
    public ResponseEntity<List<HoraireCon>> getAllHoraireCons(Pageable pageable) {
        log.debug("REST request to get a page of HoraireCons");
        Page<HoraireCon> page = horaireConService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /horaire-cons/:id} : get the "id" horaireCon.
     *
     * @param id the id of the horaireCon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horaireCon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horaire-cons/{id}")
    public ResponseEntity<HoraireCon> getHoraireCon(@PathVariable Long id) {
        log.debug("REST request to get HoraireCon : {}", id);
        Optional<HoraireCon> horaireCon = horaireConService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horaireCon);
    }

    /**
     * {@code DELETE  /horaire-cons/:id} : delete the "id" horaireCon.
     *
     * @param id the id of the horaireCon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horaire-cons/{id}")
    public ResponseEntity<Void> deleteHoraireCon(@PathVariable Long id) {
        log.debug("REST request to delete HoraireCon : {}", id);
        horaireConService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
