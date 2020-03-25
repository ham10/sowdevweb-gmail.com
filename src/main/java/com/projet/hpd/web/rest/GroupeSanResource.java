package com.projet.hpd.web.rest;

import com.projet.hpd.domain.GroupeSan;
import com.projet.hpd.service.GroupeSanService;
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
 * REST controller for managing {@link com.projet.hpd.domain.GroupeSan}.
 */
@RestController
@RequestMapping("/api")
public class GroupeSanResource {

    private final Logger log = LoggerFactory.getLogger(GroupeSanResource.class);

    private static final String ENTITY_NAME = "groupeSan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupeSanService groupeSanService;

    public GroupeSanResource(GroupeSanService groupeSanService) {
        this.groupeSanService = groupeSanService;
    }

    /**
     * {@code POST  /groupe-sans} : Create a new groupeSan.
     *
     * @param groupeSan the groupeSan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupeSan, or with status {@code 400 (Bad Request)} if the groupeSan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groupe-sans")
    public ResponseEntity<GroupeSan> createGroupeSan(@RequestBody GroupeSan groupeSan) throws URISyntaxException {
        log.debug("REST request to save GroupeSan : {}", groupeSan);
        if (groupeSan.getId() != null) {
            throw new BadRequestAlertException("A new groupeSan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupeSan result = groupeSanService.save(groupeSan);
        return ResponseEntity.created(new URI("/api/groupe-sans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groupe-sans} : Updates an existing groupeSan.
     *
     * @param groupeSan the groupeSan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupeSan,
     * or with status {@code 400 (Bad Request)} if the groupeSan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupeSan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groupe-sans")
    public ResponseEntity<GroupeSan> updateGroupeSan(@RequestBody GroupeSan groupeSan) throws URISyntaxException {
        log.debug("REST request to update GroupeSan : {}", groupeSan);
        if (groupeSan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupeSan result = groupeSanService.save(groupeSan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupeSan.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /groupe-sans} : get all the groupeSans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupeSans in body.
     */
    @GetMapping("/groupe-sans")
    public ResponseEntity<List<GroupeSan>> getAllGroupeSans(Pageable pageable) {
        log.debug("REST request to get a page of GroupeSans");
        Page<GroupeSan> page = groupeSanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /groupe-sans/:id} : get the "id" groupeSan.
     *
     * @param id the id of the groupeSan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupeSan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groupe-sans/{id}")
    public ResponseEntity<GroupeSan> getGroupeSan(@PathVariable Long id) {
        log.debug("REST request to get GroupeSan : {}", id);
        Optional<GroupeSan> groupeSan = groupeSanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupeSan);
    }

    /**
     * {@code DELETE  /groupe-sans/:id} : delete the "id" groupeSan.
     *
     * @param id the id of the groupeSan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groupe-sans/{id}")
    public ResponseEntity<Void> deleteGroupeSan(@PathVariable Long id) {
        log.debug("REST request to delete GroupeSan : {}", id);
        groupeSanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
