package com.projet.hpd.web.rest;

import com.projet.hpd.domain.Annotation;
import com.projet.hpd.service.AnnotationService;
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
 * REST controller for managing {@link com.projet.hpd.domain.Annotation}.
 */
@RestController
@RequestMapping("/api")
public class AnnotationResource {

    private final Logger log = LoggerFactory.getLogger(AnnotationResource.class);

    private static final String ENTITY_NAME = "annotation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnotationService annotationService;

    public AnnotationResource(AnnotationService annotationService) {
        this.annotationService = annotationService;
    }

    /**
     * {@code POST  /annotations} : Create a new annotation.
     *
     * @param annotation the annotation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annotation, or with status {@code 400 (Bad Request)} if the annotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annotations")
    public ResponseEntity<Annotation> createAnnotation(@RequestBody Annotation annotation) throws URISyntaxException {
        log.debug("REST request to save Annotation : {}", annotation);
        if (annotation.getId() != null) {
            throw new BadRequestAlertException("A new annotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Annotation result = annotationService.save(annotation);
        return ResponseEntity.created(new URI("/api/annotations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annotations} : Updates an existing annotation.
     *
     * @param annotation the annotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annotation,
     * or with status {@code 400 (Bad Request)} if the annotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annotations")
    public ResponseEntity<Annotation> updateAnnotation(@RequestBody Annotation annotation) throws URISyntaxException {
        log.debug("REST request to update Annotation : {}", annotation);
        if (annotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Annotation result = annotationService.save(annotation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annotation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /annotations} : get all the annotations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annotations in body.
     */
    @GetMapping("/annotations")
    public ResponseEntity<List<Annotation>> getAllAnnotations(Pageable pageable) {
        log.debug("REST request to get a page of Annotations");
        Page<Annotation> page = annotationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annotations/:id} : get the "id" annotation.
     *
     * @param id the id of the annotation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annotation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annotations/{id}")
    public ResponseEntity<Annotation> getAnnotation(@PathVariable Long id) {
        log.debug("REST request to get Annotation : {}", id);
        Optional<Annotation> annotation = annotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annotation);
    }

    /**
     * {@code DELETE  /annotations/:id} : delete the "id" annotation.
     *
     * @param id the id of the annotation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annotations/{id}")
    public ResponseEntity<Void> deleteAnnotation(@PathVariable Long id) {
        log.debug("REST request to delete Annotation : {}", id);
        annotationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
