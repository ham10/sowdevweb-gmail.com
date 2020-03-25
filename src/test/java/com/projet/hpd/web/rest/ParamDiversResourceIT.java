package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ParamDivers;
import com.projet.hpd.repository.ParamDiversRepository;
import com.projet.hpd.service.ParamDiversService;
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
 * Integration tests for the {@link ParamDiversResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ParamDiversResourceIT {

    private static final String DEFAULT_CODE_PARAM_DIVERS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARAM_DIVERS = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_PARAM_DIVERS = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PARAM_DIVERS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_PARAM_DIVERS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_PARAM_DIVERS = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALEUR_NUM_1 = 1;
    private static final Integer UPDATED_VALEUR_NUM_1 = 2;

    private static final Integer DEFAULT_VALEUR_NUM_2 = 1;
    private static final Integer UPDATED_VALEUR_NUM_2 = 2;

    private static final Integer DEFAULT_VALEUR_NUM_3 = 1;
    private static final Integer UPDATED_VALEUR_NUM_3 = 2;

    private static final String DEFAULT_VALEUR_TEXT_1 = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR_TEXT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR_TEXT_2 = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR_TEXT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR_TEXT_3 = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR_TEXT_3 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALEUR_BOOLEAN_1 = false;
    private static final Boolean UPDATED_VALEUR_BOOLEAN_1 = true;

    private static final Boolean DEFAULT_VALEUR_BOOLEAN_2 = false;
    private static final Boolean UPDATED_VALEUR_BOOLEAN_2 = true;

    private static final Boolean DEFAULT_VALEUR_BOOLEAN_3 = false;
    private static final Boolean UPDATED_VALEUR_BOOLEAN_3 = true;

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
    private ParamDiversRepository paramDiversRepository;

    @Autowired
    private ParamDiversService paramDiversService;

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

    private MockMvc restParamDiversMockMvc;

    private ParamDivers paramDivers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParamDiversResource paramDiversResource = new ParamDiversResource(paramDiversService);
        this.restParamDiversMockMvc = MockMvcBuilders.standaloneSetup(paramDiversResource)
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
    public static ParamDivers createEntity(EntityManager em) {
        ParamDivers paramDivers = new ParamDivers()
            .codeParamDivers(DEFAULT_CODE_PARAM_DIVERS)
            .libelleParamDivers(DEFAULT_LIBELLE_PARAM_DIVERS)
            .descriptionParamDivers(DEFAULT_DESCRIPTION_PARAM_DIVERS)
            .valeurNum1(DEFAULT_VALEUR_NUM_1)
            .valeurNum2(DEFAULT_VALEUR_NUM_2)
            .valeurNum3(DEFAULT_VALEUR_NUM_3)
            .valeurText1(DEFAULT_VALEUR_TEXT_1)
            .valeurText2(DEFAULT_VALEUR_TEXT_2)
            .valeurText3(DEFAULT_VALEUR_TEXT_3)
            .valeurBoolean1(DEFAULT_VALEUR_BOOLEAN_1)
            .valeurBoolean2(DEFAULT_VALEUR_BOOLEAN_2)
            .valeurBoolean3(DEFAULT_VALEUR_BOOLEAN_3)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return paramDivers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamDivers createUpdatedEntity(EntityManager em) {
        ParamDivers paramDivers = new ParamDivers()
            .codeParamDivers(UPDATED_CODE_PARAM_DIVERS)
            .libelleParamDivers(UPDATED_LIBELLE_PARAM_DIVERS)
            .descriptionParamDivers(UPDATED_DESCRIPTION_PARAM_DIVERS)
            .valeurNum1(UPDATED_VALEUR_NUM_1)
            .valeurNum2(UPDATED_VALEUR_NUM_2)
            .valeurNum3(UPDATED_VALEUR_NUM_3)
            .valeurText1(UPDATED_VALEUR_TEXT_1)
            .valeurText2(UPDATED_VALEUR_TEXT_2)
            .valeurText3(UPDATED_VALEUR_TEXT_3)
            .valeurBoolean1(UPDATED_VALEUR_BOOLEAN_1)
            .valeurBoolean2(UPDATED_VALEUR_BOOLEAN_2)
            .valeurBoolean3(UPDATED_VALEUR_BOOLEAN_3)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return paramDivers;
    }

    @BeforeEach
    public void initTest() {
        paramDivers = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamDivers() throws Exception {
        int databaseSizeBeforeCreate = paramDiversRepository.findAll().size();

        // Create the ParamDivers
        restParamDiversMockMvc.perform(post("/api/param-divers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramDivers)))
            .andExpect(status().isCreated());

        // Validate the ParamDivers in the database
        List<ParamDivers> paramDiversList = paramDiversRepository.findAll();
        assertThat(paramDiversList).hasSize(databaseSizeBeforeCreate + 1);
        ParamDivers testParamDivers = paramDiversList.get(paramDiversList.size() - 1);
        assertThat(testParamDivers.getCodeParamDivers()).isEqualTo(DEFAULT_CODE_PARAM_DIVERS);
        assertThat(testParamDivers.getLibelleParamDivers()).isEqualTo(DEFAULT_LIBELLE_PARAM_DIVERS);
        assertThat(testParamDivers.getDescriptionParamDivers()).isEqualTo(DEFAULT_DESCRIPTION_PARAM_DIVERS);
        assertThat(testParamDivers.getValeurNum1()).isEqualTo(DEFAULT_VALEUR_NUM_1);
        assertThat(testParamDivers.getValeurNum2()).isEqualTo(DEFAULT_VALEUR_NUM_2);
        assertThat(testParamDivers.getValeurNum3()).isEqualTo(DEFAULT_VALEUR_NUM_3);
        assertThat(testParamDivers.getValeurText1()).isEqualTo(DEFAULT_VALEUR_TEXT_1);
        assertThat(testParamDivers.getValeurText2()).isEqualTo(DEFAULT_VALEUR_TEXT_2);
        assertThat(testParamDivers.getValeurText3()).isEqualTo(DEFAULT_VALEUR_TEXT_3);
        assertThat(testParamDivers.isValeurBoolean1()).isEqualTo(DEFAULT_VALEUR_BOOLEAN_1);
        assertThat(testParamDivers.isValeurBoolean2()).isEqualTo(DEFAULT_VALEUR_BOOLEAN_2);
        assertThat(testParamDivers.isValeurBoolean3()).isEqualTo(DEFAULT_VALEUR_BOOLEAN_3);
        assertThat(testParamDivers.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testParamDivers.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testParamDivers.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testParamDivers.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testParamDivers.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createParamDiversWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramDiversRepository.findAll().size();

        // Create the ParamDivers with an existing ID
        paramDivers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamDiversMockMvc.perform(post("/api/param-divers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramDivers)))
            .andExpect(status().isBadRequest());

        // Validate the ParamDivers in the database
        List<ParamDivers> paramDiversList = paramDiversRepository.findAll();
        assertThat(paramDiversList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParamDivers() throws Exception {
        // Initialize the database
        paramDiversRepository.saveAndFlush(paramDivers);

        // Get all the paramDiversList
        restParamDiversMockMvc.perform(get("/api/param-divers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramDivers.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeParamDivers").value(hasItem(DEFAULT_CODE_PARAM_DIVERS)))
            .andExpect(jsonPath("$.[*].libelleParamDivers").value(hasItem(DEFAULT_LIBELLE_PARAM_DIVERS)))
            .andExpect(jsonPath("$.[*].descriptionParamDivers").value(hasItem(DEFAULT_DESCRIPTION_PARAM_DIVERS)))
            .andExpect(jsonPath("$.[*].valeurNum1").value(hasItem(DEFAULT_VALEUR_NUM_1)))
            .andExpect(jsonPath("$.[*].valeurNum2").value(hasItem(DEFAULT_VALEUR_NUM_2)))
            .andExpect(jsonPath("$.[*].valeurNum3").value(hasItem(DEFAULT_VALEUR_NUM_3)))
            .andExpect(jsonPath("$.[*].valeurText1").value(hasItem(DEFAULT_VALEUR_TEXT_1)))
            .andExpect(jsonPath("$.[*].valeurText2").value(hasItem(DEFAULT_VALEUR_TEXT_2)))
            .andExpect(jsonPath("$.[*].valeurText3").value(hasItem(DEFAULT_VALEUR_TEXT_3)))
            .andExpect(jsonPath("$.[*].valeurBoolean1").value(hasItem(DEFAULT_VALEUR_BOOLEAN_1.booleanValue())))
            .andExpect(jsonPath("$.[*].valeurBoolean2").value(hasItem(DEFAULT_VALEUR_BOOLEAN_2.booleanValue())))
            .andExpect(jsonPath("$.[*].valeurBoolean3").value(hasItem(DEFAULT_VALEUR_BOOLEAN_3.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getParamDivers() throws Exception {
        // Initialize the database
        paramDiversRepository.saveAndFlush(paramDivers);

        // Get the paramDivers
        restParamDiversMockMvc.perform(get("/api/param-divers/{id}", paramDivers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paramDivers.getId().intValue()))
            .andExpect(jsonPath("$.codeParamDivers").value(DEFAULT_CODE_PARAM_DIVERS))
            .andExpect(jsonPath("$.libelleParamDivers").value(DEFAULT_LIBELLE_PARAM_DIVERS))
            .andExpect(jsonPath("$.descriptionParamDivers").value(DEFAULT_DESCRIPTION_PARAM_DIVERS))
            .andExpect(jsonPath("$.valeurNum1").value(DEFAULT_VALEUR_NUM_1))
            .andExpect(jsonPath("$.valeurNum2").value(DEFAULT_VALEUR_NUM_2))
            .andExpect(jsonPath("$.valeurNum3").value(DEFAULT_VALEUR_NUM_3))
            .andExpect(jsonPath("$.valeurText1").value(DEFAULT_VALEUR_TEXT_1))
            .andExpect(jsonPath("$.valeurText2").value(DEFAULT_VALEUR_TEXT_2))
            .andExpect(jsonPath("$.valeurText3").value(DEFAULT_VALEUR_TEXT_3))
            .andExpect(jsonPath("$.valeurBoolean1").value(DEFAULT_VALEUR_BOOLEAN_1.booleanValue()))
            .andExpect(jsonPath("$.valeurBoolean2").value(DEFAULT_VALEUR_BOOLEAN_2.booleanValue()))
            .andExpect(jsonPath("$.valeurBoolean3").value(DEFAULT_VALEUR_BOOLEAN_3.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParamDivers() throws Exception {
        // Get the paramDivers
        restParamDiversMockMvc.perform(get("/api/param-divers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamDivers() throws Exception {
        // Initialize the database
        paramDiversService.save(paramDivers);

        int databaseSizeBeforeUpdate = paramDiversRepository.findAll().size();

        // Update the paramDivers
        ParamDivers updatedParamDivers = paramDiversRepository.findById(paramDivers.getId()).get();
        // Disconnect from session so that the updates on updatedParamDivers are not directly saved in db
        em.detach(updatedParamDivers);
        updatedParamDivers
            .codeParamDivers(UPDATED_CODE_PARAM_DIVERS)
            .libelleParamDivers(UPDATED_LIBELLE_PARAM_DIVERS)
            .descriptionParamDivers(UPDATED_DESCRIPTION_PARAM_DIVERS)
            .valeurNum1(UPDATED_VALEUR_NUM_1)
            .valeurNum2(UPDATED_VALEUR_NUM_2)
            .valeurNum3(UPDATED_VALEUR_NUM_3)
            .valeurText1(UPDATED_VALEUR_TEXT_1)
            .valeurText2(UPDATED_VALEUR_TEXT_2)
            .valeurText3(UPDATED_VALEUR_TEXT_3)
            .valeurBoolean1(UPDATED_VALEUR_BOOLEAN_1)
            .valeurBoolean2(UPDATED_VALEUR_BOOLEAN_2)
            .valeurBoolean3(UPDATED_VALEUR_BOOLEAN_3)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restParamDiversMockMvc.perform(put("/api/param-divers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParamDivers)))
            .andExpect(status().isOk());

        // Validate the ParamDivers in the database
        List<ParamDivers> paramDiversList = paramDiversRepository.findAll();
        assertThat(paramDiversList).hasSize(databaseSizeBeforeUpdate);
        ParamDivers testParamDivers = paramDiversList.get(paramDiversList.size() - 1);
        assertThat(testParamDivers.getCodeParamDivers()).isEqualTo(UPDATED_CODE_PARAM_DIVERS);
        assertThat(testParamDivers.getLibelleParamDivers()).isEqualTo(UPDATED_LIBELLE_PARAM_DIVERS);
        assertThat(testParamDivers.getDescriptionParamDivers()).isEqualTo(UPDATED_DESCRIPTION_PARAM_DIVERS);
        assertThat(testParamDivers.getValeurNum1()).isEqualTo(UPDATED_VALEUR_NUM_1);
        assertThat(testParamDivers.getValeurNum2()).isEqualTo(UPDATED_VALEUR_NUM_2);
        assertThat(testParamDivers.getValeurNum3()).isEqualTo(UPDATED_VALEUR_NUM_3);
        assertThat(testParamDivers.getValeurText1()).isEqualTo(UPDATED_VALEUR_TEXT_1);
        assertThat(testParamDivers.getValeurText2()).isEqualTo(UPDATED_VALEUR_TEXT_2);
        assertThat(testParamDivers.getValeurText3()).isEqualTo(UPDATED_VALEUR_TEXT_3);
        assertThat(testParamDivers.isValeurBoolean1()).isEqualTo(UPDATED_VALEUR_BOOLEAN_1);
        assertThat(testParamDivers.isValeurBoolean2()).isEqualTo(UPDATED_VALEUR_BOOLEAN_2);
        assertThat(testParamDivers.isValeurBoolean3()).isEqualTo(UPDATED_VALEUR_BOOLEAN_3);
        assertThat(testParamDivers.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testParamDivers.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testParamDivers.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testParamDivers.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testParamDivers.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingParamDivers() throws Exception {
        int databaseSizeBeforeUpdate = paramDiversRepository.findAll().size();

        // Create the ParamDivers

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParamDiversMockMvc.perform(put("/api/param-divers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramDivers)))
            .andExpect(status().isBadRequest());

        // Validate the ParamDivers in the database
        List<ParamDivers> paramDiversList = paramDiversRepository.findAll();
        assertThat(paramDiversList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParamDivers() throws Exception {
        // Initialize the database
        paramDiversService.save(paramDivers);

        int databaseSizeBeforeDelete = paramDiversRepository.findAll().size();

        // Delete the paramDivers
        restParamDiversMockMvc.perform(delete("/api/param-divers/{id}", paramDivers.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParamDivers> paramDiversList = paramDiversRepository.findAll();
        assertThat(paramDiversList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
