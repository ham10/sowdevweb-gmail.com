package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Etablis;
import com.projet.hpd.repository.EtablisRepository;
import com.projet.hpd.service.EtablisService;
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
 * Integration tests for the {@link EtablisResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtablisResourceIT {

    private static final String DEFAULT_CODE_ETABL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ETABL = "BBBBBBBBBB";

    private static final String DEFAULT_RAISON_SOCIALE_ETABL = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE_ETABL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_ETABL = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_ETABL = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEPHONE_ETABL = 1;
    private static final Integer UPDATED_TELEPHONE_ETABL = 2;

    private static final String DEFAULT_NINEA_ETABL = "AAAAAAAAAA";
    private static final String UPDATED_NINEA_ETABL = "BBBBBBBBBB";

    private static final String DEFAULT_RC_ETABL = "AAAAAAAAAA";
    private static final String UPDATED_RC_ETABL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ETABL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ETABL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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
    private EtablisRepository etablisRepository;

    @Autowired
    private EtablisService etablisService;

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

    private MockMvc restEtablisMockMvc;

    private Etablis etablis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtablisResource etablisResource = new EtablisResource(etablisService);
        this.restEtablisMockMvc = MockMvcBuilders.standaloneSetup(etablisResource)
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
    public static Etablis createEntity(EntityManager em) {
        Etablis etablis = new Etablis()
            .codeEtabl(DEFAULT_CODE_ETABL)
            .raisonSocialeEtabl(DEFAULT_RAISON_SOCIALE_ETABL)
            .adresseEtabl(DEFAULT_ADRESSE_ETABL)
            .telephoneEtabl(DEFAULT_TELEPHONE_ETABL)
            .nineaEtabl(DEFAULT_NINEA_ETABL)
            .rcEtabl(DEFAULT_RC_ETABL)
            .emailEtabl(DEFAULT_EMAIL_ETABL)
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return etablis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etablis createUpdatedEntity(EntityManager em) {
        Etablis etablis = new Etablis()
            .codeEtabl(UPDATED_CODE_ETABL)
            .raisonSocialeEtabl(UPDATED_RAISON_SOCIALE_ETABL)
            .adresseEtabl(UPDATED_ADRESSE_ETABL)
            .telephoneEtabl(UPDATED_TELEPHONE_ETABL)
            .nineaEtabl(UPDATED_NINEA_ETABL)
            .rcEtabl(UPDATED_RC_ETABL)
            .emailEtabl(UPDATED_EMAIL_ETABL)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return etablis;
    }

    @BeforeEach
    public void initTest() {
        etablis = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtablis() throws Exception {
        int databaseSizeBeforeCreate = etablisRepository.findAll().size();

        // Create the Etablis
        restEtablisMockMvc.perform(post("/api/etablis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etablis)))
            .andExpect(status().isCreated());

        // Validate the Etablis in the database
        List<Etablis> etablisList = etablisRepository.findAll();
        assertThat(etablisList).hasSize(databaseSizeBeforeCreate + 1);
        Etablis testEtablis = etablisList.get(etablisList.size() - 1);
        assertThat(testEtablis.getCodeEtabl()).isEqualTo(DEFAULT_CODE_ETABL);
        assertThat(testEtablis.getRaisonSocialeEtabl()).isEqualTo(DEFAULT_RAISON_SOCIALE_ETABL);
        assertThat(testEtablis.getAdresseEtabl()).isEqualTo(DEFAULT_ADRESSE_ETABL);
        assertThat(testEtablis.getTelephoneEtabl()).isEqualTo(DEFAULT_TELEPHONE_ETABL);
        assertThat(testEtablis.getNineaEtabl()).isEqualTo(DEFAULT_NINEA_ETABL);
        assertThat(testEtablis.getRcEtabl()).isEqualTo(DEFAULT_RC_ETABL);
        assertThat(testEtablis.getEmailEtabl()).isEqualTo(DEFAULT_EMAIL_ETABL);
        assertThat(testEtablis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEtablis.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEtablis.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testEtablis.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEtablis.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEtablis.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createEtablisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etablisRepository.findAll().size();

        // Create the Etablis with an existing ID
        etablis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtablisMockMvc.perform(post("/api/etablis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etablis)))
            .andExpect(status().isBadRequest());

        // Validate the Etablis in the database
        List<Etablis> etablisList = etablisRepository.findAll();
        assertThat(etablisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtablis() throws Exception {
        // Initialize the database
        etablisRepository.saveAndFlush(etablis);

        // Get all the etablisList
        restEtablisMockMvc.perform(get("/api/etablis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etablis.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeEtabl").value(hasItem(DEFAULT_CODE_ETABL)))
            .andExpect(jsonPath("$.[*].raisonSocialeEtabl").value(hasItem(DEFAULT_RAISON_SOCIALE_ETABL)))
            .andExpect(jsonPath("$.[*].adresseEtabl").value(hasItem(DEFAULT_ADRESSE_ETABL)))
            .andExpect(jsonPath("$.[*].telephoneEtabl").value(hasItem(DEFAULT_TELEPHONE_ETABL)))
            .andExpect(jsonPath("$.[*].nineaEtabl").value(hasItem(DEFAULT_NINEA_ETABL)))
            .andExpect(jsonPath("$.[*].rcEtabl").value(hasItem(DEFAULT_RC_ETABL)))
            .andExpect(jsonPath("$.[*].emailEtabl").value(hasItem(DEFAULT_EMAIL_ETABL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getEtablis() throws Exception {
        // Initialize the database
        etablisRepository.saveAndFlush(etablis);

        // Get the etablis
        restEtablisMockMvc.perform(get("/api/etablis/{id}", etablis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etablis.getId().intValue()))
            .andExpect(jsonPath("$.codeEtabl").value(DEFAULT_CODE_ETABL))
            .andExpect(jsonPath("$.raisonSocialeEtabl").value(DEFAULT_RAISON_SOCIALE_ETABL))
            .andExpect(jsonPath("$.adresseEtabl").value(DEFAULT_ADRESSE_ETABL))
            .andExpect(jsonPath("$.telephoneEtabl").value(DEFAULT_TELEPHONE_ETABL))
            .andExpect(jsonPath("$.nineaEtabl").value(DEFAULT_NINEA_ETABL))
            .andExpect(jsonPath("$.rcEtabl").value(DEFAULT_RC_ETABL))
            .andExpect(jsonPath("$.emailEtabl").value(DEFAULT_EMAIL_ETABL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEtablis() throws Exception {
        // Get the etablis
        restEtablisMockMvc.perform(get("/api/etablis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtablis() throws Exception {
        // Initialize the database
        etablisService.save(etablis);

        int databaseSizeBeforeUpdate = etablisRepository.findAll().size();

        // Update the etablis
        Etablis updatedEtablis = etablisRepository.findById(etablis.getId()).get();
        // Disconnect from session so that the updates on updatedEtablis are not directly saved in db
        em.detach(updatedEtablis);
        updatedEtablis
            .codeEtabl(UPDATED_CODE_ETABL)
            .raisonSocialeEtabl(UPDATED_RAISON_SOCIALE_ETABL)
            .adresseEtabl(UPDATED_ADRESSE_ETABL)
            .telephoneEtabl(UPDATED_TELEPHONE_ETABL)
            .nineaEtabl(UPDATED_NINEA_ETABL)
            .rcEtabl(UPDATED_RC_ETABL)
            .emailEtabl(UPDATED_EMAIL_ETABL)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restEtablisMockMvc.perform(put("/api/etablis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtablis)))
            .andExpect(status().isOk());

        // Validate the Etablis in the database
        List<Etablis> etablisList = etablisRepository.findAll();
        assertThat(etablisList).hasSize(databaseSizeBeforeUpdate);
        Etablis testEtablis = etablisList.get(etablisList.size() - 1);
        assertThat(testEtablis.getCodeEtabl()).isEqualTo(UPDATED_CODE_ETABL);
        assertThat(testEtablis.getRaisonSocialeEtabl()).isEqualTo(UPDATED_RAISON_SOCIALE_ETABL);
        assertThat(testEtablis.getAdresseEtabl()).isEqualTo(UPDATED_ADRESSE_ETABL);
        assertThat(testEtablis.getTelephoneEtabl()).isEqualTo(UPDATED_TELEPHONE_ETABL);
        assertThat(testEtablis.getNineaEtabl()).isEqualTo(UPDATED_NINEA_ETABL);
        assertThat(testEtablis.getRcEtabl()).isEqualTo(UPDATED_RC_ETABL);
        assertThat(testEtablis.getEmailEtabl()).isEqualTo(UPDATED_EMAIL_ETABL);
        assertThat(testEtablis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEtablis.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEtablis.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testEtablis.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEtablis.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEtablis.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEtablis() throws Exception {
        int databaseSizeBeforeUpdate = etablisRepository.findAll().size();

        // Create the Etablis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablisMockMvc.perform(put("/api/etablis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etablis)))
            .andExpect(status().isBadRequest());

        // Validate the Etablis in the database
        List<Etablis> etablisList = etablisRepository.findAll();
        assertThat(etablisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtablis() throws Exception {
        // Initialize the database
        etablisService.save(etablis);

        int databaseSizeBeforeDelete = etablisRepository.findAll().size();

        // Delete the etablis
        restEtablisMockMvc.perform(delete("/api/etablis/{id}", etablis.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etablis> etablisList = etablisRepository.findAll();
        assertThat(etablisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
