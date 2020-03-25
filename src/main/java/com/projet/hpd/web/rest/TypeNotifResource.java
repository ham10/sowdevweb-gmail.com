package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeNotif;
import com.projet.hpd.service.TypeNotifService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeNotif}.
 */
@RestController
@RequestMapping("/api")
public class TypeNotifResource {

    private final Logger log = LoggerFactory.getLogger(TypeNotifResource.class);

    private static final String ENTITY_NAME = "typeNotif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeNotifService typeNotifService;

    public TypeNotifResource(TypeNotifService typeNotifService) {
        this.typeNotifService = typeNotifService;
    }

    /**
     * {@code POST  /type-notifs} : Create a new typeNotif.
     *
     * @param typeNotif the typeNotif to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeNotif, or with status {@code 400 (Bad Request)} if the typeNotif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-notifs")
    public ResponseEntity<TypeNotif> createTypeNotif(@RequestBody TypeNotif typeNotif) throws URISyntaxException {
        log.debug("REST request to save TypeNotif : {}", typeNotif);
        if (typeNotif.getId() != null) {
            throw new BadRequestAlertException("A new typeNotif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeNotif result = typeNotifService.save(typeNotif);
        return ResponseEntity.created(new URI("/api/type-notifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-notifs} : Updates an existing typeNotif.
     *
     * @param typeNotif the typeNotif to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeNotif,
     * or with status {@code 400 (Bad Request)} if the typeNotif is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeNotif couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-notifs")
    public ResponseEntity<TypeNotif> updateTypeNotif(@RequestBody TypeNotif typeNotif) throws URISyntaxException {
        log.debug("REST request to update TypeNotif : {}", typeNotif);
        if (typeNotif.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeNotif result = typeNotifService.save(typeNotif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeNotif.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-notifs} : get all the typeNotifs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeNotifs in body.
     */
    @GetMapping("/type-notifs")
    public ResponseEntity<List<TypeNotif>> getAllTypeNotifs(Pageable pageable) {
        log.debug("REST request to get a page of TypeNotifs");
        Page<TypeNotif> page = typeNotifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-notifs/:id} : get the "id" typeNotif.
     *
     * @param id the id of the typeNotif to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeNotif, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-notifs/{id}")
    public ResponseEntity<TypeNotif> getTypeNotif(@PathVariable Long id) {
        log.debug("REST request to get TypeNotif : {}", id);
        Optional<TypeNotif> typeNotif = typeNotifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeNotif);
    }

    /**
     * {@code DELETE  /type-notifs/:id} : delete the "id" typeNotif.
     *
     * @param id the id of the typeNotif to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-notifs/{id}")
    public ResponseEntity<Void> deleteTypeNotif(@PathVariable Long id) {
        log.debug("REST request to delete TypeNotif : {}", id);
        typeNotifService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
