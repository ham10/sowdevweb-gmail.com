package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Antecedent;
import com.projet.hpd.repository.AntecedentRepository;
import com.projet.hpd.service.AntecedentService;
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
 * Integration tests for the {@link AntecedentResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class AntecedentResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private AntecedentRepository antecedentRepository;

    @Autowired
    private AntecedentService antecedentService;

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

    private MockMvc restAntecedentMockMvc;

    private Antecedent antecedent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AntecedentResource antecedentResource = new AntecedentResource(antecedentService);
        this.restAntecedentMockMvc = MockMvcBuilders.standaloneSetup(antecedentResource)
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
    public static Antecedent createEntity(EntityManager em) {
        Antecedent antecedent = new Antecedent()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return antecedent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Antecedent createUpdatedEntity(EntityManager em) {
        Antecedent antecedent = new Antecedent()
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return antecedent;
    }

    @BeforeEach
    public void initTest() {
        antecedent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAntecedent() throws Exception {
        int databaseSizeBeforeCreate = antecedentRepository.findAll().size();

        // Create the Antecedent
        restAntecedentMockMvc.perform(post("/api/antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedent)))
            .andExpect(status().isCreated());

        // Validate the Antecedent in the database
        List<Antecedent> antecedentList = antecedentRepository.findAll();
        assertThat(antecedentList).hasSize(databaseSizeBeforeCreate + 1);
        Antecedent testAntecedent = antecedentList.get(antecedentList.size() - 1);
        assertThat(testAntecedent.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAntecedent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAntecedent.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAntecedent.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAntecedent.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testAntecedent.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testAntecedent.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testAntecedent.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createAntecedentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antecedentRepository.findAll().size();

        // Create the Antecedent with an existing ID
        antecedent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntecedentMockMvc.perform(post("/api/antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedent)))
            .andExpect(status().isBadRequest());

        // Validate the Antecedent in the database
        List<Antecedent> antecedentList = antecedentRepository.findAll();
        assertThat(antecedentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAntecedents() throws Exception {
        // Initialize the database
        antecedentRepository.saveAndFlush(antecedent);

        // Get all the antecedentList
        restAntecedentMockMvc.perform(get("/api/antecedents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antecedent.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getAntecedent() throws Exception {
        // Initialize the database
        antecedentRepository.saveAndFlush(antecedent);

        // Get the antecedent
        restAntecedentMockMvc.perform(get("/api/antecedents/{id}", antecedent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antecedent.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAntecedent() throws Exception {
        // Get the antecedent
        restAntecedentMockMvc.perform(get("/api/antecedents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAntecedent() throws Exception {
        // Initialize the database
        antecedentService.save(antecedent);

        int databaseSizeBeforeUpdate = antecedentRepository.findAll().size();

        // Update the antecedent
        Antecedent updatedAntecedent = antecedentRepository.findById(antecedent.getId()).get();
        // Disconnect from session so that the updates on updatedAntecedent are not directly saved in db
        em.detach(updatedAntecedent);
        updatedAntecedent
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restAntecedentMockMvc.perform(put("/api/antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAntecedent)))
            .andExpect(status().isOk());

        // Validate the Antecedent in the database
        List<Antecedent> antecedentList = antecedentRepository.findAll();
        assertThat(antecedentList).hasSize(databaseSizeBeforeUpdate);
        Antecedent testAntecedent = antecedentList.get(antecedentList.size() - 1);
        assertThat(testAntecedent.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAntecedent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAntecedent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAntecedent.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAntecedent.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testAntecedent.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testAntecedent.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testAntecedent.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingAntecedent() throws Exception {
        int databaseSizeBeforeUpdate = antecedentRepository.findAll().size();

        // Create the Antecedent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntecedentMockMvc.perform(put("/api/antecedents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedent)))
            .andExpect(status().isBadRequest());

        // Validate the Antecedent in the database
        List<Antecedent> antecedentList = antecedentRepository.findAll();
        assertThat(antecedentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAntecedent() throws Exception {
        // Initialize the database
        antecedentService.save(antecedent);

        int databaseSizeBeforeDelete = antecedentRepository.findAll().size();

        // Delete the antecedent
        restAntecedentMockMvc.perform(delete("/api/antecedents/{id}", antecedent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Antecedent> antecedentList = antecedentRepository.findAll();
        assertThat(antecedentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
