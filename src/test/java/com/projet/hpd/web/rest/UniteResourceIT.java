package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Unite;
import com.projet.hpd.repository.UniteRepository;
import com.projet.hpd.service.UniteService;
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
 * Integration tests for the {@link UniteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class UniteResourceIT {

    private static final String DEFAULT_CODE_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_UNITE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_UNITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_UNITE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_UNITE = "BBBBBBBBBB";

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
    private UniteRepository uniteRepository;

    @Autowired
    private UniteService uniteService;

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

    private MockMvc restUniteMockMvc;

    private Unite unite;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UniteResource uniteResource = new UniteResource(uniteService);
        this.restUniteMockMvc = MockMvcBuilders.standaloneSetup(uniteResource)
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
    public static Unite createEntity(EntityManager em) {
        Unite unite = new Unite()
            .codeUnite(DEFAULT_CODE_UNITE)
            .libelleUnite(DEFAULT_LIBELLE_UNITE)
            .descriptionUnite(DEFAULT_DESCRIPTION_UNITE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return unite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unite createUpdatedEntity(EntityManager em) {
        Unite unite = new Unite()
            .codeUnite(UPDATED_CODE_UNITE)
            .libelleUnite(UPDATED_LIBELLE_UNITE)
            .descriptionUnite(UPDATED_DESCRIPTION_UNITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return unite;
    }

    @BeforeEach
    public void initTest() {
        unite = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnite() throws Exception {
        int databaseSizeBeforeCreate = uniteRepository.findAll().size();

        // Create the Unite
        restUniteMockMvc.perform(post("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unite)))
            .andExpect(status().isCreated());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeCreate + 1);
        Unite testUnite = uniteList.get(uniteList.size() - 1);
        assertThat(testUnite.getCodeUnite()).isEqualTo(DEFAULT_CODE_UNITE);
        assertThat(testUnite.getLibelleUnite()).isEqualTo(DEFAULT_LIBELLE_UNITE);
        assertThat(testUnite.getDescriptionUnite()).isEqualTo(DEFAULT_DESCRIPTION_UNITE);
        assertThat(testUnite.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testUnite.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testUnite.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testUnite.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testUnite.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createUniteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uniteRepository.findAll().size();

        // Create the Unite with an existing ID
        unite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniteMockMvc.perform(post("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unite)))
            .andExpect(status().isBadRequest());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUnites() throws Exception {
        // Initialize the database
        uniteRepository.saveAndFlush(unite);

        // Get all the uniteList
        restUniteMockMvc.perform(get("/api/unites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unite.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeUnite").value(hasItem(DEFAULT_CODE_UNITE)))
            .andExpect(jsonPath("$.[*].libelleUnite").value(hasItem(DEFAULT_LIBELLE_UNITE)))
            .andExpect(jsonPath("$.[*].descriptionUnite").value(hasItem(DEFAULT_DESCRIPTION_UNITE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getUnite() throws Exception {
        // Initialize the database
        uniteRepository.saveAndFlush(unite);

        // Get the unite
        restUniteMockMvc.perform(get("/api/unites/{id}", unite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unite.getId().intValue()))
            .andExpect(jsonPath("$.codeUnite").value(DEFAULT_CODE_UNITE))
            .andExpect(jsonPath("$.libelleUnite").value(DEFAULT_LIBELLE_UNITE))
            .andExpect(jsonPath("$.descriptionUnite").value(DEFAULT_DESCRIPTION_UNITE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUnite() throws Exception {
        // Get the unite
        restUniteMockMvc.perform(get("/api/unites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnite() throws Exception {
        // Initialize the database
        uniteService.save(unite);

        int databaseSizeBeforeUpdate = uniteRepository.findAll().size();

        // Update the unite
        Unite updatedUnite = uniteRepository.findById(unite.getId()).get();
        // Disconnect from session so that the updates on updatedUnite are not directly saved in db
        em.detach(updatedUnite);
        updatedUnite
            .codeUnite(UPDATED_CODE_UNITE)
            .libelleUnite(UPDATED_LIBELLE_UNITE)
            .descriptionUnite(UPDATED_DESCRIPTION_UNITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restUniteMockMvc.perform(put("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnite)))
            .andExpect(status().isOk());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeUpdate);
        Unite testUnite = uniteList.get(uniteList.size() - 1);
        assertThat(testUnite.getCodeUnite()).isEqualTo(UPDATED_CODE_UNITE);
        assertThat(testUnite.getLibelleUnite()).isEqualTo(UPDATED_LIBELLE_UNITE);
        assertThat(testUnite.getDescriptionUnite()).isEqualTo(UPDATED_DESCRIPTION_UNITE);
        assertThat(testUnite.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testUnite.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testUnite.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testUnite.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testUnite.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingUnite() throws Exception {
        int databaseSizeBeforeUpdate = uniteRepository.findAll().size();

        // Create the Unite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniteMockMvc.perform(put("/api/unites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unite)))
            .andExpect(status().isBadRequest());

        // Validate the Unite in the database
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnite() throws Exception {
        // Initialize the database
        uniteService.save(unite);

        int databaseSizeBeforeDelete = uniteRepository.findAll().size();

        // Delete the unite
        restUniteMockMvc.perform(delete("/api/unites/{id}", unite.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unite> uniteList = uniteRepository.findAll();
        assertThat(uniteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
