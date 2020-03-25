package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.FicheMedical;
import com.projet.hpd.repository.FicheMedicalRepository;
import com.projet.hpd.service.FicheMedicalService;
import com.projet.hpd.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FicheMedicalResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class FicheMedicalResourceIT {

    private static final String DEFAULT_NUMERO_FICHE_MEDICAL = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FICHE_MEDICAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CONSULTATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CONSULTATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FACTEUR_RISQUE = "AAAAAAAAAA";
    private static final String UPDATED_FACTEUR_RISQUE = "BBBBBBBBBB";

    private static final String DEFAULT_REGIME_ALIMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_REGIME_ALIMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAGNOSTIC = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSTIC = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMMANDATIONS = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMANDATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRES = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_PROCHAIN_RV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PROCHAIN_RV = LocalDate.now(ZoneId.systemDefault());

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
    private FicheMedicalRepository ficheMedicalRepository;

    @Mock
    private FicheMedicalRepository ficheMedicalRepositoryMock;

    @Mock
    private FicheMedicalService ficheMedicalServiceMock;

    @Autowired
    private FicheMedicalService ficheMedicalService;

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

    private MockMvc restFicheMedicalMockMvc;

    private FicheMedical ficheMedical;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FicheMedicalResource ficheMedicalResource = new FicheMedicalResource(ficheMedicalService);
        this.restFicheMedicalMockMvc = MockMvcBuilders.standaloneSetup(ficheMedicalResource)
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
    public static FicheMedical createEntity(EntityManager em) {
        FicheMedical ficheMedical = new FicheMedical()
            .numeroFicheMedical(DEFAULT_NUMERO_FICHE_MEDICAL)
            .dateConsultation(DEFAULT_DATE_CONSULTATION)
            .facteurRisque(DEFAULT_FACTEUR_RISQUE)
            .regimeAlimentaire(DEFAULT_REGIME_ALIMENTAIRE)
            .diagnostic(DEFAULT_DIAGNOSTIC)
            .recommandations(DEFAULT_RECOMMANDATIONS)
            .commentaires(DEFAULT_COMMENTAIRES)
            .dateProchainRV(DEFAULT_DATE_PROCHAIN_RV)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return ficheMedical;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FicheMedical createUpdatedEntity(EntityManager em) {
        FicheMedical ficheMedical = new FicheMedical()
            .numeroFicheMedical(UPDATED_NUMERO_FICHE_MEDICAL)
            .dateConsultation(UPDATED_DATE_CONSULTATION)
            .facteurRisque(UPDATED_FACTEUR_RISQUE)
            .regimeAlimentaire(UPDATED_REGIME_ALIMENTAIRE)
            .diagnostic(UPDATED_DIAGNOSTIC)
            .recommandations(UPDATED_RECOMMANDATIONS)
            .commentaires(UPDATED_COMMENTAIRES)
            .dateProchainRV(UPDATED_DATE_PROCHAIN_RV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return ficheMedical;
    }

    @BeforeEach
    public void initTest() {
        ficheMedical = createEntity(em);
    }

    @Test
    @Transactional
    public void createFicheMedical() throws Exception {
        int databaseSizeBeforeCreate = ficheMedicalRepository.findAll().size();

        // Create the FicheMedical
        restFicheMedicalMockMvc.perform(post("/api/fiche-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ficheMedical)))
            .andExpect(status().isCreated());

        // Validate the FicheMedical in the database
        List<FicheMedical> ficheMedicalList = ficheMedicalRepository.findAll();
        assertThat(ficheMedicalList).hasSize(databaseSizeBeforeCreate + 1);
        FicheMedical testFicheMedical = ficheMedicalList.get(ficheMedicalList.size() - 1);
        assertThat(testFicheMedical.getNumeroFicheMedical()).isEqualTo(DEFAULT_NUMERO_FICHE_MEDICAL);
        assertThat(testFicheMedical.getDateConsultation()).isEqualTo(DEFAULT_DATE_CONSULTATION);
        assertThat(testFicheMedical.getFacteurRisque()).isEqualTo(DEFAULT_FACTEUR_RISQUE);
        assertThat(testFicheMedical.getRegimeAlimentaire()).isEqualTo(DEFAULT_REGIME_ALIMENTAIRE);
        assertThat(testFicheMedical.getDiagnostic()).isEqualTo(DEFAULT_DIAGNOSTIC);
        assertThat(testFicheMedical.getRecommandations()).isEqualTo(DEFAULT_RECOMMANDATIONS);
        assertThat(testFicheMedical.getCommentaires()).isEqualTo(DEFAULT_COMMENTAIRES);
        assertThat(testFicheMedical.getDateProchainRV()).isEqualTo(DEFAULT_DATE_PROCHAIN_RV);
        assertThat(testFicheMedical.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFicheMedical.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testFicheMedical.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFicheMedical.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testFicheMedical.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createFicheMedicalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ficheMedicalRepository.findAll().size();

        // Create the FicheMedical with an existing ID
        ficheMedical.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFicheMedicalMockMvc.perform(post("/api/fiche-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ficheMedical)))
            .andExpect(status().isBadRequest());

        // Validate the FicheMedical in the database
        List<FicheMedical> ficheMedicalList = ficheMedicalRepository.findAll();
        assertThat(ficheMedicalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFicheMedicals() throws Exception {
        // Initialize the database
        ficheMedicalRepository.saveAndFlush(ficheMedical);

        // Get all the ficheMedicalList
        restFicheMedicalMockMvc.perform(get("/api/fiche-medicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ficheMedical.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroFicheMedical").value(hasItem(DEFAULT_NUMERO_FICHE_MEDICAL)))
            .andExpect(jsonPath("$.[*].dateConsultation").value(hasItem(DEFAULT_DATE_CONSULTATION.toString())))
            .andExpect(jsonPath("$.[*].facteurRisque").value(hasItem(DEFAULT_FACTEUR_RISQUE)))
            .andExpect(jsonPath("$.[*].regimeAlimentaire").value(hasItem(DEFAULT_REGIME_ALIMENTAIRE)))
            .andExpect(jsonPath("$.[*].diagnostic").value(hasItem(DEFAULT_DIAGNOSTIC)))
            .andExpect(jsonPath("$.[*].recommandations").value(hasItem(DEFAULT_RECOMMANDATIONS)))
            .andExpect(jsonPath("$.[*].commentaires").value(hasItem(DEFAULT_COMMENTAIRES)))
            .andExpect(jsonPath("$.[*].dateProchainRV").value(hasItem(DEFAULT_DATE_PROCHAIN_RV.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFicheMedicalsWithEagerRelationshipsIsEnabled() throws Exception {
        FicheMedicalResource ficheMedicalResource = new FicheMedicalResource(ficheMedicalServiceMock);
        when(ficheMedicalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFicheMedicalMockMvc = MockMvcBuilders.standaloneSetup(ficheMedicalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFicheMedicalMockMvc.perform(get("/api/fiche-medicals?eagerload=true"))
        .andExpect(status().isOk());

        verify(ficheMedicalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFicheMedicalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        FicheMedicalResource ficheMedicalResource = new FicheMedicalResource(ficheMedicalServiceMock);
            when(ficheMedicalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFicheMedicalMockMvc = MockMvcBuilders.standaloneSetup(ficheMedicalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFicheMedicalMockMvc.perform(get("/api/fiche-medicals?eagerload=true"))
        .andExpect(status().isOk());

            verify(ficheMedicalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFicheMedical() throws Exception {
        // Initialize the database
        ficheMedicalRepository.saveAndFlush(ficheMedical);

        // Get the ficheMedical
        restFicheMedicalMockMvc.perform(get("/api/fiche-medicals/{id}", ficheMedical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ficheMedical.getId().intValue()))
            .andExpect(jsonPath("$.numeroFicheMedical").value(DEFAULT_NUMERO_FICHE_MEDICAL))
            .andExpect(jsonPath("$.dateConsultation").value(DEFAULT_DATE_CONSULTATION.toString()))
            .andExpect(jsonPath("$.facteurRisque").value(DEFAULT_FACTEUR_RISQUE))
            .andExpect(jsonPath("$.regimeAlimentaire").value(DEFAULT_REGIME_ALIMENTAIRE))
            .andExpect(jsonPath("$.diagnostic").value(DEFAULT_DIAGNOSTIC))
            .andExpect(jsonPath("$.recommandations").value(DEFAULT_RECOMMANDATIONS))
            .andExpect(jsonPath("$.commentaires").value(DEFAULT_COMMENTAIRES))
            .andExpect(jsonPath("$.dateProchainRV").value(DEFAULT_DATE_PROCHAIN_RV.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFicheMedical() throws Exception {
        // Get the ficheMedical
        restFicheMedicalMockMvc.perform(get("/api/fiche-medicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFicheMedical() throws Exception {
        // Initialize the database
        ficheMedicalService.save(ficheMedical);

        int databaseSizeBeforeUpdate = ficheMedicalRepository.findAll().size();

        // Update the ficheMedical
        FicheMedical updatedFicheMedical = ficheMedicalRepository.findById(ficheMedical.getId()).get();
        // Disconnect from session so that the updates on updatedFicheMedical are not directly saved in db
        em.detach(updatedFicheMedical);
        updatedFicheMedical
            .numeroFicheMedical(UPDATED_NUMERO_FICHE_MEDICAL)
            .dateConsultation(UPDATED_DATE_CONSULTATION)
            .facteurRisque(UPDATED_FACTEUR_RISQUE)
            .regimeAlimentaire(UPDATED_REGIME_ALIMENTAIRE)
            .diagnostic(UPDATED_DIAGNOSTIC)
            .recommandations(UPDATED_RECOMMANDATIONS)
            .commentaires(UPDATED_COMMENTAIRES)
            .dateProchainRV(UPDATED_DATE_PROCHAIN_RV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restFicheMedicalMockMvc.perform(put("/api/fiche-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFicheMedical)))
            .andExpect(status().isOk());

        // Validate the FicheMedical in the database
        List<FicheMedical> ficheMedicalList = ficheMedicalRepository.findAll();
        assertThat(ficheMedicalList).hasSize(databaseSizeBeforeUpdate);
        FicheMedical testFicheMedical = ficheMedicalList.get(ficheMedicalList.size() - 1);
        assertThat(testFicheMedical.getNumeroFicheMedical()).isEqualTo(UPDATED_NUMERO_FICHE_MEDICAL);
        assertThat(testFicheMedical.getDateConsultation()).isEqualTo(UPDATED_DATE_CONSULTATION);
        assertThat(testFicheMedical.getFacteurRisque()).isEqualTo(UPDATED_FACTEUR_RISQUE);
        assertThat(testFicheMedical.getRegimeAlimentaire()).isEqualTo(UPDATED_REGIME_ALIMENTAIRE);
        assertThat(testFicheMedical.getDiagnostic()).isEqualTo(UPDATED_DIAGNOSTIC);
        assertThat(testFicheMedical.getRecommandations()).isEqualTo(UPDATED_RECOMMANDATIONS);
        assertThat(testFicheMedical.getCommentaires()).isEqualTo(UPDATED_COMMENTAIRES);
        assertThat(testFicheMedical.getDateProchainRV()).isEqualTo(UPDATED_DATE_PROCHAIN_RV);
        assertThat(testFicheMedical.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFicheMedical.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testFicheMedical.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFicheMedical.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testFicheMedical.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingFicheMedical() throws Exception {
        int databaseSizeBeforeUpdate = ficheMedicalRepository.findAll().size();

        // Create the FicheMedical

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFicheMedicalMockMvc.perform(put("/api/fiche-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ficheMedical)))
            .andExpect(status().isBadRequest());

        // Validate the FicheMedical in the database
        List<FicheMedical> ficheMedicalList = ficheMedicalRepository.findAll();
        assertThat(ficheMedicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFicheMedical() throws Exception {
        // Initialize the database
        ficheMedicalService.save(ficheMedical);

        int databaseSizeBeforeDelete = ficheMedicalRepository.findAll().size();

        // Delete the ficheMedical
        restFicheMedicalMockMvc.perform(delete("/api/fiche-medicals/{id}", ficheMedical.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FicheMedical> ficheMedicalList = ficheMedicalRepository.findAll();
        assertThat(ficheMedicalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
