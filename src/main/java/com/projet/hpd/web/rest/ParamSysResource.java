package com.projet.hpd.web.rest;

import com.projet.hpd.domain.ParamSys;
import com.projet.hpd.service.ParamSysService;
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
 * REST controller for managing {@link com.projet.hpd.domain.ParamSys}.
 */
@RestController
@RequestMapping("/api")
public class ParamSysResource {

    private final Logger log = LoggerFactory.getLogger(ParamSysResource.class);

    private static final String ENTITY_NAME = "paramSys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamSysService paramSysService;

    public ParamSysResource(ParamSysService paramSysService) {
        this.paramSysService = paramSysService;
    }

    /**
     * {@code POST  /param-sys} : Create a new paramSys.
     *
     * @param paramSys the paramSys to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramSys, or with status {@code 400 (Bad Request)} if the paramSys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/param-sys")
    public ResponseEntity<ParamSys> createParamSys(@RequestBody ParamSys paramSys) throws URISyntaxException {
        log.debug("REST request to save ParamSys : {}", paramSys);
        if (paramSys.getId() != null) {
            throw new BadRequestAlertException("A new paramSys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamSys result = paramSysService.save(paramSys);
        return ResponseEntity.created(new URI("/api/param-sys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /param-sys} : Updates an existing paramSys.
     *
     * @param paramSys the paramSys to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramSys,
     * or with status {@code 400 (Bad Request)} if the paramSys is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramSys couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/param-sys")
    public ResponseEntity<ParamSys> updateParamSys(@RequestBody ParamSys paramSys) throws URISyntaxException {
        log.debug("REST request to update ParamSys : {}", paramSys);
        if (paramSys.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamSys result = paramSysService.save(paramSys);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paramSys.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /param-sys} : get all the paramSys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramSys in body.
     */
    @GetMapping("/param-sys")
    public ResponseEntity<List<ParamSys>> getAllParamSys(Pageable pageable) {
        log.debug("REST request to get a page of ParamSys");
        Page<ParamSys> page = paramSysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /param-sys/:id} : get the "id" paramSys.
     *
     * @param id the id of the paramSys to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramSys, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/param-sys/{id}")
    public ResponseEntity<ParamSys> getParamSys(@PathVariable Long id) {
        log.debug("REST request to get ParamSys : {}", id);
        Optional<ParamSys> paramSys = paramSysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramSys);
    }

    /**
     * {@code DELETE  /param-sys/:id} : delete the "id" paramSys.
     *
     * @param id the id of the paramSys to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/param-sys/{id}")
    public ResponseEntity<Void> deleteParamSys(@PathVariable Long id) {
        log.debug("REST request to delete ParamSys : {}", id);
        paramSysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
