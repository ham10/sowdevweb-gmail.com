package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.FormeProd;
import com.projet.hpd.repository.FormeProdRepository;
import com.projet.hpd.service.FormeProdService;
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
 * Integration tests for the {@link FormeProdResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class FormeProdResourceIT {

    private static final String DEFAULT_CODE_FORME_PROD = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FORME_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_FORME_PROD = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_FORME_PROD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private FormeProdRepository formeProdRepository;

    @Autowired
    private FormeProdService formeProdService;

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

    private MockMvc restFormeProdMockMvc;

    private FormeProd formeProd;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormeProdResource formeProdResource = new FormeProdResource(formeProdService);
        this.restFormeProdMockMvc = MockMvcBuilders.standaloneSetup(formeProdResource)
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
    public static FormeProd createEntity(EntityManager em) {
        FormeProd formeProd = new FormeProd()
            .codeFormeProd(DEFAULT_CODE_FORME_PROD)
            .libelleFormeProd(DEFAULT_LIBELLE_FORME_PROD)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return formeProd;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormeProd createUpdatedEntity(EntityManager em) {
        FormeProd formeProd = new FormeProd()
            .codeFormeProd(UPDATED_CODE_FORME_PROD)
            .libelleFormeProd(UPDATED_LIBELLE_FORME_PROD)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return formeProd;
    }

    @BeforeEach
    public void initTest() {
        formeProd = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormeProd() throws Exception {
        int databaseSizeBeforeCreate = formeProdRepository.findAll().size();

        // Create the FormeProd
        restFormeProdMockMvc.perform(post("/api/forme-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formeProd)))
            .andExpect(status().isCreated());

        // Validate the FormeProd in the database
        List<FormeProd> formeProdList = formeProdRepository.findAll();
        assertThat(formeProdList).hasSize(databaseSizeBeforeCreate + 1);
        FormeProd testFormeProd = formeProdList.get(formeProdList.size() - 1);
        assertThat(testFormeProd.getCodeFormeProd()).isEqualTo(DEFAULT_CODE_FORME_PROD);
        assertThat(testFormeProd.getLibelleFormeProd()).isEqualTo(DEFAULT_LIBELLE_FORME_PROD);
        assertThat(testFormeProd.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFormeProd.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testFormeProd.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFormeProd.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testFormeProd.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createFormeProdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formeProdRepository.findAll().size();

        // Create the FormeProd with an existing ID
        formeProd.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormeProdMockMvc.perform(post("/api/forme-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formeProd)))
            .andExpect(status().isBadRequest());

        // Validate the FormeProd in the database
        List<FormeProd> formeProdList = formeProdRepository.findAll();
        assertThat(formeProdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFormeProds() throws Exception {
        // Initialize the database
        formeProdRepository.saveAndFlush(formeProd);

        // Get all the formeProdList
        restFormeProdMockMvc.perform(get("/api/forme-prods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formeProd.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeFormeProd").value(hasItem(DEFAULT_CODE_FORME_PROD)))
            .andExpect(jsonPath("$.[*].libelleFormeProd").value(hasItem(DEFAULT_LIBELLE_FORME_PROD)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getFormeProd() throws Exception {
        // Initialize the database
        formeProdRepository.saveAndFlush(formeProd);

        // Get the formeProd
        restFormeProdMockMvc.perform(get("/api/forme-prods/{id}", formeProd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formeProd.getId().intValue()))
            .andExpect(jsonPath("$.codeFormeProd").value(DEFAULT_CODE_FORME_PROD))
            .andExpect(jsonPath("$.libelleFormeProd").value(DEFAULT_LIBELLE_FORME_PROD))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFormeProd() throws Exception {
        // Get the formeProd
        restFormeProdMockMvc.perform(get("/api/forme-prods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormeProd() throws Exception {
        // Initialize the database
        formeProdService.save(formeProd);

        int databaseSizeBeforeUpdate = formeProdRepository.findAll().size();

        // Update the formeProd
        FormeProd updatedFormeProd = formeProdRepository.findById(formeProd.getId()).get();
        // Disconnect from session so that the updates on updatedFormeProd are not directly saved in db
        em.detach(updatedFormeProd);
        updatedFormeProd
            .codeFormeProd(UPDATED_CODE_FORME_PROD)
            .libelleFormeProd(UPDATED_LIBELLE_FORME_PROD)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restFormeProdMockMvc.perform(put("/api/forme-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormeProd)))
            .andExpect(status().isOk());

        // Validate the FormeProd in the database
        List<FormeProd> formeProdList = formeProdRepository.findAll();
        assertThat(formeProdList).hasSize(databaseSizeBeforeUpdate);
        FormeProd testFormeProd = formeProdList.get(formeProdList.size() - 1);
        assertThat(testFormeProd.getCodeFormeProd()).isEqualTo(UPDATED_CODE_FORME_PROD);
        assertThat(testFormeProd.getLibelleFormeProd()).isEqualTo(UPDATED_LIBELLE_FORME_PROD);
        assertThat(testFormeProd.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFormeProd.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testFormeProd.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFormeProd.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testFormeProd.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingFormeProd() throws Exception {
        int databaseSizeBeforeUpdate = formeProdRepository.findAll().size();

        // Create the FormeProd

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormeProdMockMvc.perform(put("/api/forme-prods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formeProd)))
            .andExpect(status().isBadRequest());

        // Validate the FormeProd in the database
        List<FormeProd> formeProdList = formeProdRepository.findAll();
        assertThat(formeProdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormeProd() throws Exception {
        // Initialize the database
        formeProdService.save(formeProd);

        int databaseSizeBeforeDelete = formeProdRepository.findAll().size();

        // Delete the formeProd
        restFormeProdMockMvc.perform(delete("/api/forme-prods/{id}", formeProd.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormeProd> formeProdList = formeProdRepository.findAll();
        assertThat(formeProdList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
