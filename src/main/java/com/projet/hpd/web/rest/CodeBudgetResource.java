package com.projet.hpd.web.rest;

import com.projet.hpd.domain.CodeBudget;
import com.projet.hpd.service.CodeBudgetService;
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
 * REST controller for managing {@link com.projet.hpd.domain.CodeBudget}.
 */
@RestController
@RequestMapping("/api")
public class CodeBudgetResource {

    private final Logger log = LoggerFactory.getLogger(CodeBudgetResource.class);

    private static final String ENTITY_NAME = "codeBudget";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeBudgetService codeBudgetService;

    public CodeBudgetResource(CodeBudgetService codeBudgetService) {
        this.codeBudgetService = codeBudgetService;
    }

    /**
     * {@code POST  /code-budgets} : Create a new codeBudget.
     *
     * @param codeBudget the codeBudget to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeBudget, or with status {@code 400 (Bad Request)} if the codeBudget has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/code-budgets")
    public ResponseEntity<CodeBudget> createCodeBudget(@RequestBody CodeBudget codeBudget) throws URISyntaxException {
        log.debug("REST request to save CodeBudget : {}", codeBudget);
        if (codeBudget.getId() != null) {
            throw new BadRequestAlertException("A new codeBudget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodeBudget result = codeBudgetService.save(codeBudget);
        return ResponseEntity.created(new URI("/api/code-budgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /code-budgets} : Updates an existing codeBudget.
     *
     * @param codeBudget the codeBudget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeBudget,
     * or with status {@code 400 (Bad Request)} if the codeBudget is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeBudget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/code-budgets")
    public ResponseEntity<CodeBudget> updateCodeBudget(@RequestBody CodeBudget codeBudget) throws URISyntaxException {
        log.debug("REST request to update CodeBudget : {}", codeBudget);
        if (codeBudget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodeBudget result = codeBudgetService.save(codeBudget);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeBudget.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /code-budgets} : get all the codeBudgets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codeBudgets in body.
     */
    @GetMapping("/code-budgets")
    public ResponseEntity<List<CodeBudget>> getAllCodeBudgets(Pageable pageable) {
        log.debug("REST request to get a page of CodeBudgets");
        Page<CodeBudget> page = codeBudgetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /code-budgets/:id} : get the "id" codeBudget.
     *
     * @param id the id of the codeBudget to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeBudget, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/code-budgets/{id}")
    public ResponseEntity<CodeBudget> getCodeBudget(@PathVariable Long id) {
        log.debug("REST request to get CodeBudget : {}", id);
        Optional<CodeBudget> codeBudget = codeBudgetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codeBudget);
    }

    /**
     * {@code DELETE  /code-budgets/:id} : delete the "id" codeBudget.
     *
     * @param id the id of the codeBudget to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/code-budgets/{id}")
    public ResponseEntity<Void> deleteCodeBudget(@PathVariable Long id) {
        log.debug("REST request to delete CodeBudget : {}", id);
        codeBudgetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
