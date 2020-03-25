package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ParamCode;
import com.projet.hpd.repository.ParamCodeRepository;
import com.projet.hpd.service.ParamCodeService;
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
 * Integration tests for the {@link ParamCodeResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ParamCodeResourceIT {

    private static final String DEFAULT_CODE_PARAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARAM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_PARAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PARAM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_PARAM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_PARAM_CODE = "BBBBBBBBBB";

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
    private ParamCodeRepository paramCodeRepository;

    @Autowired
    private ParamCodeService paramCodeService;

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

    private MockMvc restParamCodeMockMvc;

    private ParamCode paramCode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParamCodeResource paramCodeResource = new ParamCodeResource(paramCodeService);
        this.restParamCodeMockMvc = MockMvcBuilders.standaloneSetup(paramCodeResource)
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
    public static ParamCode createEntity(EntityManager em) {
        ParamCode paramCode = new ParamCode()
            .codeParamCode(DEFAULT_CODE_PARAM_CODE)
            .libelleParamCode(DEFAULT_LIBELLE_PARAM_CODE)
            .descriptionParamCode(DEFAULT_DESCRIPTION_PARAM_CODE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return paramCode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamCode createUpdatedEntity(EntityManager em) {
        ParamCode paramCode = new ParamCode()
            .codeParamCode(UPDATED_CODE_PARAM_CODE)
            .libelleParamCode(UPDATED_LIBELLE_PARAM_CODE)
            .descriptionParamCode(UPDATED_DESCRIPTION_PARAM_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return paramCode;
    }

    @BeforeEach
    public void initTest() {
        paramCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamCode() throws Exception {
        int databaseSizeBeforeCreate = paramCodeRepository.findAll().size();

        // Create the ParamCode
        restParamCodeMockMvc.perform(post("/api/param-codes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramCode)))
            .andExpect(status().isCreated());

        // Validate the ParamCode in the database
        List<ParamCode> paramCodeList = paramCodeRepository.findAll();
        assertThat(paramCodeList).hasSize(databaseSizeBeforeCreate + 1);
        ParamCode testParamCode = paramCodeList.get(paramCodeList.size() - 1);
        assertThat(testParamCode.getCodeParamCode()).isEqualTo(DEFAULT_CODE_PARAM_CODE);
        assertThat(testParamCode.getLibelleParamCode()).isEqualTo(DEFAULT_LIBELLE_PARAM_CODE);
        assertThat(testParamCode.getDescriptionParamCode()).isEqualTo(DEFAULT_DESCRIPTION_PARAM_CODE);
        assertThat(testParamCode.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testParamCode.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testParamCode.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testParamCode.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testParamCode.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createParamCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramCodeRepository.findAll().size();

        // Create the ParamCode with an existing ID
        paramCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamCodeMockMvc.perform(post("/api/param-codes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramCode)))
            .andExpect(status().isBadRequest());

        // Validate the ParamCode in the database
        List<ParamCode> paramCodeList = paramCodeRepository.findAll();
        assertThat(paramCodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParamCodes() throws Exception {
        // Initialize the database
        paramCodeRepository.saveAndFlush(paramCode);

        // Get all the paramCodeList
        restParamCodeMockMvc.perform(get("/api/param-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeParamCode").value(hasItem(DEFAULT_CODE_PARAM_CODE)))
            .andExpect(jsonPath("$.[*].libelleParamCode").value(hasItem(DEFAULT_LIBELLE_PARAM_CODE)))
            .andExpect(jsonPath("$.[*].descriptionParamCode").value(hasItem(DEFAULT_DESCRIPTION_PARAM_CODE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getParamCode() throws Exception {
        // Initialize the database
        paramCodeRepository.saveAndFlush(paramCode);

        // Get the paramCode
        restParamCodeMockMvc.perform(get("/api/param-codes/{id}", paramCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paramCode.getId().intValue()))
            .andExpect(jsonPath("$.codeParamCode").value(DEFAULT_CODE_PARAM_CODE))
            .andExpect(jsonPath("$.libelleParamCode").value(DEFAULT_LIBELLE_PARAM_CODE))
            .andExpect(jsonPath("$.descriptionParamCode").value(DEFAULT_DESCRIPTION_PARAM_CODE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParamCode() throws Exception {
        // Get the paramCode
        restParamCodeMockMvc.perform(get("/api/param-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamCode() throws Exception {
        // Initialize the database
        paramCodeService.save(paramCode);

        int databaseSizeBeforeUpdate = paramCodeRepository.findAll().size();

        // Update the paramCode
        ParamCode updatedParamCode = paramCodeRepository.findById(paramCode.getId()).get();
        // Disconnect from session so that the updates on updatedParamCode are not directly saved in db
        em.detach(updatedParamCode);
        updatedParamCode
            .codeParamCode(UPDATED_CODE_PARAM_CODE)
            .libelleParamCode(UPDATED_LIBELLE_PARAM_CODE)
            .descriptionParamCode(UPDATED_DESCRIPTION_PARAM_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restParamCodeMockMvc.perform(put("/api/param-codes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParamCode)))
            .andExpect(status().isOk());

        // Validate the ParamCode in the database
        List<ParamCode> paramCodeList = paramCodeRepository.findAll();
        assertThat(paramCodeList).hasSize(databaseSizeBeforeUpdate);
        ParamCode testParamCode = paramCodeList.get(paramCodeList.size() - 1);
        assertThat(testParamCode.getCodeParamCode()).isEqualTo(UPDATED_CODE_PARAM_CODE);
        assertThat(testParamCode.getLibelleParamCode()).isEqualTo(UPDATED_LIBELLE_PARAM_CODE);
        assertThat(testParamCode.getDescriptionParamCode()).isEqualTo(UPDATED_DESCRIPTION_PARAM_CODE);
        assertThat(testParamCode.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testParamCode.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testParamCode.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testParamCode.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testParamCode.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingParamCode() throws Exception {
        int databaseSizeBeforeUpdate = paramCodeRepository.findAll().size();

        // Create the ParamCode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParamCodeMockMvc.perform(put("/api/param-codes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramCode)))
            .andExpect(status().isBadRequest());

        // Validate the ParamCode in the database
        List<ParamCode> paramCodeList = paramCodeRepository.findAll();
        assertThat(paramCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParamCode() throws Exception {
        // Initialize the database
        paramCodeService.save(paramCode);

        int databaseSizeBeforeDelete = paramCodeRepository.findAll().size();

        // Delete the paramCode
        restParamCodeMockMvc.perform(delete("/api/param-codes/{id}", paramCode.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParamCode> paramCodeList = paramCodeRepository.findAll();
        assertThat(paramCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
