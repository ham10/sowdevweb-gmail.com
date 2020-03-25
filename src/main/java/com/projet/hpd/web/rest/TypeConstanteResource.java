package com.projet.hpd.web.rest;

import com.projet.hpd.domain.TypeConstante;
import com.projet.hpd.service.TypeConstanteService;
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
 * REST controller for managing {@link com.projet.hpd.domain.TypeConstante}.
 */
@RestController
@RequestMapping("/api")
public class TypeConstanteResource {

    private final Logger log = LoggerFactory.getLogger(TypeConstanteResource.class);

    private static final String ENTITY_NAME = "typeConstante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeConstanteService typeConstanteService;

    public TypeConstanteResource(TypeConstanteService typeConstanteService) {
        this.typeConstanteService = typeConstanteService;
    }

    /**
     * {@code POST  /type-constantes} : Create a new typeConstante.
     *
     * @param typeConstante the typeConstante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeConstante, or with status {@code 400 (Bad Request)} if the typeConstante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-constantes")
    public ResponseEntity<TypeConstante> createTypeConstante(@RequestBody TypeConstante typeConstante) throws URISyntaxException {
        log.debug("REST request to save TypeConstante : {}", typeConstante);
        if (typeConstante.getId() != null) {
            throw new BadRequestAlertException("A new typeConstante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeConstante result = typeConstanteService.save(typeConstante);
        return ResponseEntity.created(new URI("/api/type-constantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-constantes} : Updates an existing typeConstante.
     *
     * @param typeConstante the typeConstante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeConstante,
     * or with status {@code 400 (Bad Request)} if the typeConstante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeConstante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-constantes")
    public ResponseEntity<TypeConstante> updateTypeConstante(@RequestBody TypeConstante typeConstante) throws URISyntaxException {
        log.debug("REST request to update TypeConstante : {}", typeConstante);
        if (typeConstante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeConstante result = typeConstanteService.save(typeConstante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeConstante.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-constantes} : get all the typeConstantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeConstantes in body.
     */
    @GetMapping("/type-constantes")
    public ResponseEntity<List<TypeConstante>> getAllTypeConstantes(Pageable pageable) {
        log.debug("REST request to get a page of TypeConstantes");
        Page<TypeConstante> page = typeConstanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-constantes/:id} : get the "id" typeConstante.
     *
     * @param id the id of the typeConstante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeConstante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-constantes/{id}")
    public ResponseEntity<TypeConstante> getTypeConstante(@PathVariable Long id) {
        log.debug("REST request to get TypeConstante : {}", id);
        Optional<TypeConstante> typeConstante = typeConstanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeConstante);
    }

    /**
     * {@code DELETE  /type-constantes/:id} : delete the "id" typeConstante.
     *
     * @param id the id of the typeConstante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-constantes/{id}")
    public ResponseEntity<Void> deleteTypeConstante(@PathVariable Long id) {
        log.debug("REST request to delete TypeConstante : {}", id);
        typeConstanteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
