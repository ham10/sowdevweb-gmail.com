package com.projet.hpd.web.rest;

import com.projet.hpd.domain.ParamCode;
import com.projet.hpd.service.ParamCodeService;
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
 * REST controller for managing {@link com.projet.hpd.domain.ParamCode}.
 */
@RestController
@RequestMapping("/api")
public class ParamCodeResource {

    private final Logger log = LoggerFactory.getLogger(ParamCodeResource.class);

    private static final String ENTITY_NAME = "paramCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamCodeService paramCodeService;

    public ParamCodeResource(ParamCodeService paramCodeService) {
        this.paramCodeService = paramCodeService;
    }

    /**
     * {@code POST  /param-codes} : Create a new paramCode.
     *
     * @param paramCode the paramCode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramCode, or with status {@code 400 (Bad Request)} if the paramCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/param-codes")
    public ResponseEntity<ParamCode> createParamCode(@RequestBody ParamCode paramCode) throws URISyntaxException {
        log.debug("REST request to save ParamCode : {}", paramCode);
        if (paramCode.getId() != null) {
            throw new BadRequestAlertException("A new paramCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamCode result = paramCodeService.save(paramCode);
        return ResponseEntity.created(new URI("/api/param-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /param-codes} : Updates an existing paramCode.
     *
     * @param paramCode the paramCode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramCode,
     * or with status {@code 400 (Bad Request)} if the paramCode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramCode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/param-codes")
    public ResponseEntity<ParamCode> updateParamCode(@RequestBody ParamCode paramCode) throws URISyntaxException {
        log.debug("REST request to update ParamCode : {}", paramCode);
        if (paramCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamCode result = paramCodeService.save(paramCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paramCode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /param-codes} : get all the paramCodes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramCodes in body.
     */
    @GetMapping("/param-codes")
    public ResponseEntity<List<ParamCode>> getAllParamCodes(Pageable pageable) {
        log.debug("REST request to get a page of ParamCodes");
        Page<ParamCode> page = paramCodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /param-codes/:id} : get the "id" paramCode.
     *
     * @param id the id of the paramCode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramCode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/param-codes/{id}")
    public ResponseEntity<ParamCode> getParamCode(@PathVariable Long id) {
        log.debug("REST request to get ParamCode : {}", id);
        Optional<ParamCode> paramCode = paramCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramCode);
    }

    /**
     * {@code DELETE  /param-codes/:id} : delete the "id" paramCode.
     *
     * @param id the id of the paramCode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/param-codes/{id}")
    public ResponseEntity<Void> deleteParamCode(@PathVariable Long id) {
        log.debug("REST request to delete ParamCode : {}", id);
        paramCodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
