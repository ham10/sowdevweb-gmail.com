package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.CodeBudget;
import com.projet.hpd.repository.CodeBudgetRepository;
import com.projet.hpd.service.CodeBudgetService;
import com.projet.hpd.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CodeBudgetResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CodeBudgetResourceIT {

    private static final Integer DEFAULT_CODE_CODE_BUDGET = 1;
    private static final Integer UPDATED_CODE_CODE_BUDGET = 2;

    private static final String DEFAULT_LIBELLE_CODE_BUDGET = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CODE_BUDGET = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private CodeBudgetRepository codeBudgetRepository;

    @Autowired
    private CodeBudgetService codeBudgetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCodeBudgetMockMvc;

    private CodeBudget codeBudget;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodeBudgetResource codeBudgetResource = new CodeBudgetResource(codeBudgetService);
        this.restCodeBudgetMockMvc = MockMvcBuilders.standaloneSetup(codeBudgetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeBudget createEntity(EntityManager em) {
        CodeBudget codeBudget = new CodeBudget()
            .codeCodeBudget(DEFAULT_CODE_CODE_BUDGET)
            .libelleCodeBudget(DEFAULT_LIBELLE_CODE_BUDGET)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return codeBudget;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeBudget createUpdatedEntity(EntityManager em) {
        CodeBudget codeBudget = new CodeBudget()
            .codeCodeBudget(UPDATED_CODE_CODE_BUDGET)
            .libelleCodeBudget(UPDATED_LIBELLE_CODE_BUDGET)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return codeBudget;
    }

    @BeforeEach
    public void initTest() {
        codeBudget = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodeBudget() throws Exception {
        int databaseSizeBeforeCreate = codeBudgetRepository.findAll().size();

        // Create the CodeBudget
        restCodeBudgetMockMvc.perform(post("/api/code-budgets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codeBudget)))
            .andExpect(status().isCreated());

        // Validate the CodeBudget in the database
        List<CodeBudget> codeBudgetList = codeBudgetRepository.findAll();
        assertThat(codeBudgetList).hasSize(databaseSizeBeforeCreate + 1);
        CodeBudget testCodeBudget = codeBudgetList.get(codeBudgetList.size() - 1);
        assertThat(testCodeBudget.getCodeCodeBudget()).isEqualTo(DEFAULT_CODE_CODE_BUDGET);
        assertThat(testCodeBudget.getLibelleCodeBudget()).isEqualTo(DEFAULT_LIBELLE_CODE_BUDGET);
        assertThat(testCodeBudget.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCodeBudget.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCodeBudget.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testCodeBudget.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCodeBudget.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCodeBudget.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCodeBudgetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codeBudgetRepository.findAll().size();

        // Create the CodeBudget with an existing ID
        codeBudget.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeBudgetMockMvc.perform(post("/api/code-budgets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codeBudget)))
            .andExpect(status().isBadRequest());

        // Validate the CodeBudget in the database
        List<CodeBudget> codeBudgetList = codeBudgetRepository.findAll();
        assertThat(codeBudgetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCodeBudgets() throws Exception {
        // Initialize the database
        codeBudgetRepository.saveAndFlush(codeBudget);

        // Get all the codeBudgetList
        restCodeBudgetMockMvc.perform(get("/api/code-budgets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeBudget.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCodeBudget").value(hasItem(DEFAULT_CODE_CODE_BUDGET)))
            .andExpect(jsonPath("$.[*].libelleCodeBudget").value(hasItem(DEFAULT_LIBELLE_CODE_BUDGET)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCodeBudget() throws Exception {
        // Initialize the database
        codeBudgetRepository.saveAndFlush(codeBudget);

        // Get the codeBudget
        restCodeBudgetMockMvc.perform(get("/api/code-budgets/{id}", codeBudget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codeBudget.getId().intValue()))
            .andExpect(jsonPath("$.codeCodeBudget").value(DEFAULT_CODE_CODE_BUDGET))
            .andExpect(jsonPath("$.libelleCodeBudget").value(DEFAULT_LIBELLE_CODE_BUDGET))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCodeBudget() throws Exception {
        // Get the codeBudget
        restCodeBudgetMockMvc.perform(get("/api/code-budgets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodeBudget() throws Exception {
        // Initialize the database
        codeBudgetService.save(codeBudget);

        int databaseSizeBeforeUpdate = codeBudgetRepository.findAll().size();

        // Update the codeBudget
        CodeBudget updatedCodeBudget = codeBudgetRepository.findById(codeBudget.getId()).get();
        // Disconnect from session so that the updates on updatedCodeBudget are not directly saved in db
        em.detach(updatedCodeBudget);
        updatedCodeBudget
            .codeCodeBudget(UPDATED_CODE_CODE_BUDGET)
            .libelleCodeBudget(UPDATED_LIBELLE_CODE_BUDGET)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCodeBudgetMockMvc.perform(put("/api/code-budgets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCodeBudget)))
            .andExpect(status().isOk());

        // Validate the CodeBudget in the database
        List<CodeBudget> codeBudgetList = codeBudgetRepository.findAll();
        assertThat(codeBudgetList).hasSize(databaseSizeBeforeUpdate);
        CodeBudget testCodeBudget = codeBudgetList.get(codeBudgetList.size() - 1);
        assertThat(testCodeBudget.getCodeCodeBudget()).isEqualTo(UPDATED_CODE_CODE_BUDGET);
        assertThat(testCodeBudget.getLibelleCodeBudget()).isEqualTo(UPDATED_LIBELLE_CODE_BUDGET);
        assertThat(testCodeBudget.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCodeBudget.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCodeBudget.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testCodeBudget.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCodeBudget.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCodeBudget.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCodeBudget() throws Exception {
        int databaseSizeBeforeUpdate = codeBudgetRepository.findAll().size();

        // Create the CodeBudget

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeBudgetMockMvc.perform(put("/api/code-budgets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codeBudget)))
            .andExpect(status().isBadRequest());

        // Validate the CodeBudget in the database
        List<CodeBudget> codeBudgetList = codeBudgetRepository.findAll();
        assertThat(codeBudgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCodeBudget() throws Exception {
        // Initialize the database
        codeBudgetService.save(codeBudget);

        int databaseSizeBeforeDelete = codeBudgetRepository.findAll().size();

        // Delete the codeBudget
        restCodeBudgetMockMvc.perform(delete("/api/code-budgets/{id}", codeBudget.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodeBudget> codeBudgetList = codeBudgetRepository.findAll();
        assertThat(codeBudgetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
