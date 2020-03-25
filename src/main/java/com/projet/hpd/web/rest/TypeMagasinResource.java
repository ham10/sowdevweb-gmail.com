package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeMagasin;
import com.projet.hpd.service.TypeMagasinService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeMagasin}.
 */
@RestController
@RequestMapping("/api")
public class TypeMagasinResource {

    private final Logger log = LoggerFactory.getLogger(TypeMagasinResource.class);

    private static final String ENTITY_NAME = "typeMagasin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeMagasinService typeMagasinService;

    public TypeMagasinResource(TypeMagasinService typeMagasinService) {
        this.typeMagasinService = typeMagasinService;
    }

    /**
     * {@code POST  /type-magasins} : Create a new typeMagasin.
     *
     * @param typeMagasin the typeMagasin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeMagasin, or with status {@code 400 (Bad Request)} if the typeMagasin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-magasins")
    public ResponseEntity<TypeMagasin> createTypeMagasin(@RequestBody TypeMagasin typeMagasin) throws URISyntaxException {
        log.debug("REST request to save TypeMagasin : {}", typeMagasin);
        if (typeMagasin.getId() != null) {
            throw new BadRequestAlertException("A new typeMagasin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeMagasin result = typeMagasinService.save(typeMagasin);
        return ResponseEntity.created(new URI("/api/type-magasins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-magasins} : Updates an existing typeMagasin.
     *
     * @param typeMagasin the typeMagasin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeMagasin,
     * or with status {@code 400 (Bad Request)} if the typeMagasin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeMagasin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-magasins")
    public ResponseEntity<TypeMagasin> updateTypeMagasin(@RequestBody TypeMagasin typeMagasin) throws URISyntaxException {
        log.debug("REST request to update TypeMagasin : {}", typeMagasin);
        if (typeMagasin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeMagasin result = typeMagasinService.save(typeMagasin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeMagasin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-magasins} : get all the typeMagasins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeMagasins in body.
     */
    @GetMapping("/type-magasins")
    public ResponseEntity<List<TypeMagasin>> getAllTypeMagasins(Pageable pageable) {
        log.debug("REST request to get a page of TypeMagasins");
        Page<TypeMagasin> page = typeMagasinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-magasins/:id} : get the "id" typeMagasin.
     *
     * @param id the id of the typeMagasin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeMagasin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-magasins/{id}")
    public ResponseEntity<TypeMagasin> getTypeMagasin(@PathVariable Long id) {
        log.debug("REST request to get TypeMagasin : {}", id);
        Optional<TypeMagasin> typeMagasin = typeMagasinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeMagasin);
    }

    /**
     * {@code DELETE  /type-magasins/:id} : delete the "id" typeMagasin.
     *
     * @param id the id of the typeMagasin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-magasins/{id}")
    public ResponseEntity<Void> deleteTypeMagasin(@PathVariable Long id) {
        log.debug("REST request to delete TypeMagasin : {}", id);
        typeMagasinService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
