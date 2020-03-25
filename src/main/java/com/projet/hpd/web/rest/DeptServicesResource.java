package com.projet.hpd.web.rest;

import com.projet.hpd.domain.DeptServices;
import com.projet.hpd.service.DeptServicesService;
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
 * REST controller for managing {@link com.projet.hpd.domain.DeptServices}.
 */
@RestController
@RequestMapping("/api")
public class DeptServicesResource {

    private final Logger log = LoggerFactory.getLogger(DeptServicesResource.class);

    private static final String ENTITY_NAME = "deptServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeptServicesService deptServicesService;

    public DeptServicesResource(DeptServicesService deptServicesService) {
        this.deptServicesService = deptServicesService;
    }

    /**
     * {@code POST  /dept-services} : Create a new deptServices.
     *
     * @param deptServices the deptServices to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deptServices, or with status {@code 400 (Bad Request)} if the deptServices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dept-services")
    public ResponseEntity<DeptServices> createDeptServices(@RequestBody DeptServices deptServices) throws URISyntaxException {
        log.debug("REST request to save DeptServices : {}", deptServices);
        if (deptServices.getId() != null) {
            throw new BadRequestAlertException("A new deptServices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeptServices result = deptServicesService.save(deptServices);
        return ResponseEntity.created(new URI("/api/dept-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dept-services} : Updates an existing deptServices.
     *
     * @param deptServices the deptServices to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deptServices,
     * or with status {@code 400 (Bad Request)} if the deptServices is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deptServices couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dept-services")
    public ResponseEntity<DeptServices> updateDeptServices(@RequestBody DeptServices deptServices) throws URISyntaxException {
        log.debug("REST request to update DeptServices : {}", deptServices);
        if (deptServices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeptServices result = deptServicesService.save(deptServices);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deptServices.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dept-services} : get all the deptServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deptServices in body.
     */
    @GetMapping("/dept-services")
    public ResponseEntity<List<DeptServices>> getAllDeptServices(Pageable pageable) {
        log.debug("REST request to get a page of DeptServices");
        Page<DeptServices> page = deptServicesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dept-services/:id} : get the "id" deptServices.
     *
     * @param id the id of the deptServices to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deptServices, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dept-services/{id}")
    public ResponseEntity<DeptServices> getDeptServices(@PathVariable Long id) {
        log.debug("REST request to get DeptServices : {}", id);
        Optional<DeptServices> deptServices = deptServicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deptServices);
    }

    /**
     * {@code DELETE  /dept-services/:id} : delete the "id" deptServices.
     *
     * @param id the id of the deptServices to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dept-services/{id}")
    public ResponseEntity<Void> deleteDeptServices(@PathVariable Long id) {
        log.debug("REST request to delete DeptServices : {}", id);
        deptServicesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
