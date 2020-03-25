package com.projet.hpd.web.rest;

import com.projet.hpd.domain.ClasseProd;
import com.projet.hpd.service.ClasseProdService;
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
 * REST controller for managing {@link com.projet.hpd.domain.ClasseProd}.
 */
@RestController
@RequestMapping("/api")
public class ClasseProdResource {

    private final Logger log = LoggerFactory.getLogger(ClasseProdResource.class);

    private static final String ENTITY_NAME = "classeProd";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasseProdService classeProdService;

    public ClasseProdResource(ClasseProdService classeProdService) {
        this.classeProdService = classeProdService;
    }

    /**
     * {@code POST  /classe-prods} : Create a new classeProd.
     *
     * @param classeProd the classeProd to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classeProd, or with status {@code 400 (Bad Request)} if the classeProd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classe-prods")
    public ResponseEntity<ClasseProd> createClasseProd(@RequestBody ClasseProd classeProd) throws URISyntaxException {
        log.debug("REST request to save ClasseProd : {}", classeProd);
        if (classeProd.getId() != null) {
            throw new BadRequestAlertException("A new classeProd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasseProd result = classeProdService.save(classeProd);
        return ResponseEntity.created(new URI("/api/classe-prods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classe-prods} : Updates an existing classeProd.
     *
     * @param classeProd the classeProd to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classeProd,
     * or with status {@code 400 (Bad Request)} if the classeProd is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classeProd couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classe-prods")
    public ResponseEntity<ClasseProd> updateClasseProd(@RequestBody ClasseProd classeProd) throws URISyntaxException {
        log.debug("REST request to update ClasseProd : {}", classeProd);
        if (classeProd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClasseProd result = classeProdService.save(classeProd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classeProd.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /classe-prods} : get all the classeProds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classeProds in body.
     */
    @GetMapping("/classe-prods")
    public ResponseEntity<List<ClasseProd>> getAllClasseProds(Pageable pageable) {
        log.debug("REST request to get a page of ClasseProds");
        Page<ClasseProd> page = classeProdService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classe-prods/:id} : get the "id" classeProd.
     *
     * @param id the id of the classeProd to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classeProd, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classe-prods/{id}")
    public ResponseEntity<ClasseProd> getClasseProd(@PathVariable Long id) {
        log.debug("REST request to get ClasseProd : {}", id);
        Optional<ClasseProd> classeProd = classeProdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classeProd);
    }

    /**
     * {@code DELETE  /classe-prods/:id} : delete the "id" classeProd.
     *
     * @param id the id of the classeProd to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classe-prods/{id}")
    public ResponseEntity<Void> deleteClasseProd(@PathVariable Long id) {
        log.debug("REST request to delete ClasseProd : {}", id);
        classeProdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
