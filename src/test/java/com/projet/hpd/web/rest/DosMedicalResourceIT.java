package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.DosMedical;
import com.projet.hpd.repository.DosMedicalRepository;
import com.projet.hpd.service.DosMedicalService;
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
 * Integration tests for the {@link DosMedicalResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class DosMedicalResourceIT {

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMERO_DOSSIER_DOS_MEDICAL = 1;
    private static final Integer UPDATED_NUMERO_DOSSIER_DOS_MEDICAL = 2;

    private static final Integer DEFAULT_NIVEAU_DEPENDANCE = 1;
    private static final Integer UPDATED_NIVEAU_DEPENDANCE = 2;

    private static final Integer DEFAULT_ETAT_CONSCIENCE = 1;
    private static final Integer UPDATED_ETAT_CONSCIENCE = 2;

    private static final Integer DEFAULT_ETAT_CUTANE = 1;
    private static final Integer UPDATED_ETAT_CUTANE = 2;

    private static final String DEFAULT_INTOLERANCE_MEDIC = "AAAAAAAAAA";
    private static final String UPDATED_INTOLERANCE_MEDIC = "BBBBBBBBBB";

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
    private DosMedicalRepository dosMedicalRepository;

    @Mock
    private DosMedicalRepository dosMedicalRepositoryMock;

    @Mock
    private DosMedicalService dosMedicalServiceMock;

    @Autowired
    private DosMedicalService dosMedicalService;

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

    private MockMvc restDosMedicalMockMvc;

    private DosMedical dosMedical;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DosMedicalResource dosMedicalResource = new DosMedicalResource(dosMedicalService);
        this.restDosMedicalMockMvc = MockMvcBuilders.standaloneSetup(dosMedicalResource)
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
    public static DosMedical createEntity(EntityManager em) {
        DosMedical dosMedical = new DosMedical()
            .dateCreation(DEFAULT_DATE_CREATION)
            .numeroDossierDosMedical(DEFAULT_NUMERO_DOSSIER_DOS_MEDICAL)
            .niveauDependance(DEFAULT_NIVEAU_DEPENDANCE)
            .etatConscience(DEFAULT_ETAT_CONSCIENCE)
            .etatCutane(DEFAULT_ETAT_CUTANE)
            .intoleranceMedic(DEFAULT_INTOLERANCE_MEDIC)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return dosMedical;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DosMedical createUpdatedEntity(EntityManager em) {
        DosMedical dosMedical = new DosMedical()
            .dateCreation(UPDATED_DATE_CREATION)
            .numeroDossierDosMedical(UPDATED_NUMERO_DOSSIER_DOS_MEDICAL)
            .niveauDependance(UPDATED_NIVEAU_DEPENDANCE)
            .etatConscience(UPDATED_ETAT_CONSCIENCE)
            .etatCutane(UPDATED_ETAT_CUTANE)
            .intoleranceMedic(UPDATED_INTOLERANCE_MEDIC)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return dosMedical;
    }

    @BeforeEach
    public void initTest() {
        dosMedical = createEntity(em);
    }

    @Test
    @Transactional
    public void createDosMedical() throws Exception {
        int databaseSizeBeforeCreate = dosMedicalRepository.findAll().size();

        // Create the DosMedical
        restDosMedicalMockMvc.perform(post("/api/dos-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dosMedical)))
            .andExpect(status().isCreated());

        // Validate the DosMedical in the database
        List<DosMedical> dosMedicalList = dosMedicalRepository.findAll();
        assertThat(dosMedicalList).hasSize(databaseSizeBeforeCreate + 1);
        DosMedical testDosMedical = dosMedicalList.get(dosMedicalList.size() - 1);
        assertThat(testDosMedical.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testDosMedical.getNumeroDossierDosMedical()).isEqualTo(DEFAULT_NUMERO_DOSSIER_DOS_MEDICAL);
        assertThat(testDosMedical.getNiveauDependance()).isEqualTo(DEFAULT_NIVEAU_DEPENDANCE);
        assertThat(testDosMedical.getEtatConscience()).isEqualTo(DEFAULT_ETAT_CONSCIENCE);
        assertThat(testDosMedical.getEtatCutane()).isEqualTo(DEFAULT_ETAT_CUTANE);
        assertThat(testDosMedical.getIntoleranceMedic()).isEqualTo(DEFAULT_INTOLERANCE_MEDIC);
        assertThat(testDosMedical.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDosMedical.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testDosMedical.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testDosMedical.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testDosMedical.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createDosMedicalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dosMedicalRepository.findAll().size();

        // Create the DosMedical with an existing ID
        dosMedical.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDosMedicalMockMvc.perform(post("/api/dos-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dosMedical)))
            .andExpect(status().isBadRequest());

        // Validate the DosMedical in the database
        List<DosMedical> dosMedicalList = dosMedicalRepository.findAll();
        assertThat(dosMedicalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDosMedicals() throws Exception {
        // Initialize the database
        dosMedicalRepository.saveAndFlush(dosMedical);

        // Get all the dosMedicalList
        restDosMedicalMockMvc.perform(get("/api/dos-medicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dosMedical.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].numeroDossierDosMedical").value(hasItem(DEFAULT_NUMERO_DOSSIER_DOS_MEDICAL)))
            .andExpect(jsonPath("$.[*].niveauDependance").value(hasItem(DEFAULT_NIVEAU_DEPENDANCE)))
            .andExpect(jsonPath("$.[*].etatConscience").value(hasItem(DEFAULT_ETAT_CONSCIENCE)))
            .andExpect(jsonPath("$.[*].etatCutane").value(hasItem(DEFAULT_ETAT_CUTANE)))
            .andExpect(jsonPath("$.[*].intoleranceMedic").value(hasItem(DEFAULT_INTOLERANCE_MEDIC)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDosMedicalsWithEagerRelationshipsIsEnabled() throws Exception {
        DosMedicalResource dosMedicalResource = new DosMedicalResource(dosMedicalServiceMock);
        when(dosMedicalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDosMedicalMockMvc = MockMvcBuilders.standaloneSetup(dosMedicalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDosMedicalMockMvc.perform(get("/api/dos-medicals?eagerload=true"))
        .andExpect(status().isOk());

        verify(dosMedicalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDosMedicalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DosMedicalResource dosMedicalResource = new DosMedicalResource(dosMedicalServiceMock);
            when(dosMedicalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDosMedicalMockMvc = MockMvcBuilders.standaloneSetup(dosMedicalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDosMedicalMockMvc.perform(get("/api/dos-medicals?eagerload=true"))
        .andExpect(status().isOk());

            verify(dosMedicalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDosMedical() throws Exception {
        // Initialize the database
        dosMedicalRepository.saveAndFlush(dosMedical);

        // Get the dosMedical
        restDosMedicalMockMvc.perform(get("/api/dos-medicals/{id}", dosMedical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dosMedical.getId().intValue()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.numeroDossierDosMedical").value(DEFAULT_NUMERO_DOSSIER_DOS_MEDICAL))
            .andExpect(jsonPath("$.niveauDependance").value(DEFAULT_NIVEAU_DEPENDANCE))
            .andExpect(jsonPath("$.etatConscience").value(DEFAULT_ETAT_CONSCIENCE))
            .andExpect(jsonPath("$.etatCutane").value(DEFAULT_ETAT_CUTANE))
            .andExpect(jsonPath("$.intoleranceMedic").value(DEFAULT_INTOLERANCE_MEDIC))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDosMedical() throws Exception {
        // Get the dosMedical
        restDosMedicalMockMvc.perform(get("/api/dos-medicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDosMedical() throws Exception {
        // Initialize the database
        dosMedicalService.save(dosMedical);

        int databaseSizeBeforeUpdate = dosMedicalRepository.findAll().size();

        // Update the dosMedical
        DosMedical updatedDosMedical = dosMedicalRepository.findById(dosMedical.getId()).get();
        // Disconnect from session so that the updates on updatedDosMedical are not directly saved in db
        em.detach(updatedDosMedical);
        updatedDosMedical
            .dateCreation(UPDATED_DATE_CREATION)
            .numeroDossierDosMedical(UPDATED_NUMERO_DOSSIER_DOS_MEDICAL)
            .niveauDependance(UPDATED_NIVEAU_DEPENDANCE)
            .etatConscience(UPDATED_ETAT_CONSCIENCE)
            .etatCutane(UPDATED_ETAT_CUTANE)
            .intoleranceMedic(UPDATED_INTOLERANCE_MEDIC)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restDosMedicalMockMvc.perform(put("/api/dos-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDosMedical)))
            .andExpect(status().isOk());

        // Validate the DosMedical in the database
        List<DosMedical> dosMedicalList = dosMedicalRepository.findAll();
        assertThat(dosMedicalList).hasSize(databaseSizeBeforeUpdate);
        DosMedical testDosMedical = dosMedicalList.get(dosMedicalList.size() - 1);
        assertThat(testDosMedical.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDosMedical.getNumeroDossierDosMedical()).isEqualTo(UPDATED_NUMERO_DOSSIER_DOS_MEDICAL);
        assertThat(testDosMedical.getNiveauDependance()).isEqualTo(UPDATED_NIVEAU_DEPENDANCE);
        assertThat(testDosMedical.getEtatConscience()).isEqualTo(UPDATED_ETAT_CONSCIENCE);
        assertThat(testDosMedical.getEtatCutane()).isEqualTo(UPDATED_ETAT_CUTANE);
        assertThat(testDosMedical.getIntoleranceMedic()).isEqualTo(UPDATED_INTOLERANCE_MEDIC);
        assertThat(testDosMedical.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDosMedical.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testDosMedical.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testDosMedical.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testDosMedical.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingDosMedical() throws Exception {
        int databaseSizeBeforeUpdate = dosMedicalRepository.findAll().size();

        // Create the DosMedical

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDosMedicalMockMvc.perform(put("/api/dos-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dosMedical)))
            .andExpect(status().isBadRequest());

        // Validate the DosMedical in the database
        List<DosMedical> dosMedicalList = dosMedicalRepository.findAll();
        assertThat(dosMedicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDosMedical() throws Exception {
        // Initialize the database
        dosMedicalService.save(dosMedical);

        int databaseSizeBeforeDelete = dosMedicalRepository.findAll().size();

        // Delete the dosMedical
        restDosMedicalMockMvc.perform(delete("/api/dos-medicals/{id}", dosMedical.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DosMedical> dosMedicalList = dosMedicalRepository.findAll();
        assertThat(dosMedicalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
