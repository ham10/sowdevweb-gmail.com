package com.projet.hpd.web.rest;

import com.projet.hpd.domain.ParamDivers;
import com.projet.hpd.service.ParamDiversService;
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
 * REST controller for managing {@link com.projet.hpd.domain.ParamDivers}.
 */
@RestController
@RequestMapping("/api")
public class ParamDiversResource {

    private final Logger log = LoggerFactory.getLogger(ParamDiversResource.class);

    private static final String ENTITY_NAME = "paramDivers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamDiversService paramDiversService;

    public ParamDiversResource(ParamDiversService paramDiversService) {
        this.paramDiversService = paramDiversService;
    }

    /**
     * {@code POST  /param-divers} : Create a new paramDivers.
     *
     * @param paramDivers the paramDivers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramDivers, or with status {@code 400 (Bad Request)} if the paramDivers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/param-divers")
    public ResponseEntity<ParamDivers> createParamDivers(@RequestBody ParamDivers paramDivers) throws URISyntaxException {
        log.debug("REST request to save ParamDivers : {}", paramDivers);
        if (paramDivers.getId() != null) {
            throw new BadRequestAlertException("A new paramDivers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamDivers result = paramDiversService.save(paramDivers);
        return ResponseEntity.created(new URI("/api/param-divers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /param-divers} : Updates an existing paramDivers.
     *
     * @param paramDivers the paramDivers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramDivers,
     * or with status {@code 400 (Bad Request)} if the paramDivers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramDivers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/param-divers")
    public ResponseEntity<ParamDivers> updateParamDivers(@RequestBody ParamDivers paramDivers) throws URISyntaxException {
        log.debug("REST request to update ParamDivers : {}", paramDivers);
        if (paramDivers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamDivers result = paramDiversService.save(paramDivers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paramDivers.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /param-divers} : get all the paramDivers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramDivers in body.
     */
    @GetMapping("/param-divers")
    public ResponseEntity<List<ParamDivers>> getAllParamDivers(Pageable pageable) {
        log.debug("REST request to get a page of ParamDivers");
        Page<ParamDivers> page = paramDiversService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /param-divers/:id} : get the "id" paramDivers.
     *
     * @param id the id of the paramDivers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramDivers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/param-divers/{id}")
    public ResponseEntity<ParamDivers> getParamDivers(@PathVariable Long id) {
        log.debug("REST request to get ParamDivers : {}", id);
        Optional<ParamDivers> paramDivers = paramDiversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramDivers);
    }

    /**
     * {@code DELETE  /param-divers/:id} : delete the "id" paramDivers.
     *
     * @param id the id of the paramDivers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/param-divers/{id}")
    public ResponseEntity<Void> deleteParamDivers(@PathVariable Long id) {
        log.debug("REST request to delete ParamDivers : {}", id);
        paramDiversService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
