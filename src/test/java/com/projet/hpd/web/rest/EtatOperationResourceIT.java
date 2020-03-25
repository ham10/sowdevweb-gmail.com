package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.EtatOperation;
import com.projet.hpd.repository.EtatOperationRepository;
import com.projet.hpd.service.EtatOperationService;
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
 * Integration tests for the {@link EtatOperationResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtatOperationResourceIT {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

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
    private EtatOperationRepository etatOperationRepository;

    @Autowired
    private EtatOperationService etatOperationService;

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

    private MockMvc restEtatOperationMockMvc;

    private EtatOperation etatOperation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatOperationResource etatOperationResource = new EtatOperationResource(etatOperationService);
        this.restEtatOperationMockMvc = MockMvcBuilders.standaloneSetup(etatOperationResource)
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
    public static EtatOperation createEntity(EntityManager em) {
        EtatOperation etatOperation = new EtatOperation()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return etatOperation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatOperation createUpdatedEntity(EntityManager em) {
        EtatOperation etatOperation = new EtatOperation()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return etatOperation;
    }

    @BeforeEach
    public void initTest() {
        etatOperation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatOperation() throws Exception {
        int databaseSizeBeforeCreate = etatOperationRepository.findAll().size();

        // Create the EtatOperation
        restEtatOperationMockMvc.perform(post("/api/etat-operations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatOperation)))
            .andExpect(status().isCreated());

        // Validate the EtatOperation in the database
        List<EtatOperation> etatOperationList = etatOperationRepository.findAll();
        assertThat(etatOperationList).hasSize(databaseSizeBeforeCreate + 1);
        EtatOperation testEtatOperation = etatOperationList.get(etatOperationList.size() - 1);
        assertThat(testEtatOperation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEtatOperation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEtatOperation.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEtatOperation.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testEtatOperation.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testEtatOperation.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEtatOperation.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEtatOperation.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createEtatOperationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatOperationRepository.findAll().size();

        // Create the EtatOperation with an existing ID
        etatOperation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatOperationMockMvc.perform(post("/api/etat-operations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatOperation)))
            .andExpect(status().isBadRequest());

        // Validate the EtatOperation in the database
        List<EtatOperation> etatOperationList = etatOperationRepository.findAll();
        assertThat(etatOperationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtatOperations() throws Exception {
        // Initialize the database
        etatOperationRepository.saveAndFlush(etatOperation);

        // Get all the etatOperationList
        restEtatOperationMockMvc.perform(get("/api/etat-operations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatOperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getEtatOperation() throws Exception {
        // Initialize the database
        etatOperationRepository.saveAndFlush(etatOperation);

        // Get the etatOperation
        restEtatOperationMockMvc.perform(get("/api/etat-operations/{id}", etatOperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatOperation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEtatOperation() throws Exception {
        // Get the etatOperation
        restEtatOperationMockMvc.perform(get("/api/etat-operations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatOperation() throws Exception {
        // Initialize the database
        etatOperationService.save(etatOperation);

        int databaseSizeBeforeUpdate = etatOperationRepository.findAll().size();

        // Update the etatOperation
        EtatOperation updatedEtatOperation = etatOperationRepository.findById(etatOperation.getId()).get();
        // Disconnect from session so that the updates on updatedEtatOperation are not directly saved in db
        em.detach(updatedEtatOperation);
        updatedEtatOperation
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restEtatOperationMockMvc.perform(put("/api/etat-operations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatOperation)))
            .andExpect(status().isOk());

        // Validate the EtatOperation in the database
        List<EtatOperation> etatOperationList = etatOperationRepository.findAll();
        assertThat(etatOperationList).hasSize(databaseSizeBeforeUpdate);
        EtatOperation testEtatOperation = etatOperationList.get(etatOperationList.size() - 1);
        assertThat(testEtatOperation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEtatOperation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtatOperation.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEtatOperation.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testEtatOperation.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testEtatOperation.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEtatOperation.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEtatOperation.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatOperation() throws Exception {
        int databaseSizeBeforeUpdate = etatOperationRepository.findAll().size();

        // Create the EtatOperation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatOperationMockMvc.perform(put("/api/etat-operations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatOperation)))
            .andExpect(status().isBadRequest());

        // Validate the EtatOperation in the database
        List<EtatOperation> etatOperationList = etatOperationRepository.findAll();
        assertThat(etatOperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatOperation() throws Exception {
        // Initialize the database
        etatOperationService.save(etatOperation);

        int databaseSizeBeforeDelete = etatOperationRepository.findAll().size();

        // Delete the etatOperation
        restEtatOperationMockMvc.perform(delete("/api/etat-operations/{id}", etatOperation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatOperation> etatOperationList = etatOperationRepository.findAll();
        assertThat(etatOperationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
