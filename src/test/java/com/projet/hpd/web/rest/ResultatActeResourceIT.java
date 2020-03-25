package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ResultatActe;
import com.projet.hpd.repository.ResultatActeRepository;
import com.projet.hpd.service.ResultatActeService;
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
 * Integration tests for the {@link ResultatActeResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ResultatActeResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAT = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

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
    private ResultatActeRepository resultatActeRepository;

    @Mock
    private ResultatActeRepository resultatActeRepositoryMock;

    @Mock
    private ResultatActeService resultatActeServiceMock;

    @Autowired
    private ResultatActeService resultatActeService;

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

    private MockMvc restResultatActeMockMvc;

    private ResultatActe resultatActe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultatActeResource resultatActeResource = new ResultatActeResource(resultatActeService);
        this.restResultatActeMockMvc = MockMvcBuilders.standaloneSetup(resultatActeResource)
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
    public static ResultatActe createEntity(EntityManager em) {
        ResultatActe resultatActe = new ResultatActe()
            .numero(DEFAULT_NUMERO)
            .resultat(DEFAULT_RESULTAT)
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return resultatActe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultatActe createUpdatedEntity(EntityManager em) {
        ResultatActe resultatActe = new ResultatActe()
            .numero(UPDATED_NUMERO)
            .resultat(UPDATED_RESULTAT)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return resultatActe;
    }

    @BeforeEach
    public void initTest() {
        resultatActe = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultatActe() throws Exception {
        int databaseSizeBeforeCreate = resultatActeRepository.findAll().size();

        // Create the ResultatActe
        restResultatActeMockMvc.perform(post("/api/resultat-actes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultatActe)))
            .andExpect(status().isCreated());

        // Validate the ResultatActe in the database
        List<ResultatActe> resultatActeList = resultatActeRepository.findAll();
        assertThat(resultatActeList).hasSize(databaseSizeBeforeCreate + 1);
        ResultatActe testResultatActe = resultatActeList.get(resultatActeList.size() - 1);
        assertThat(testResultatActe.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testResultatActe.getResultat()).isEqualTo(DEFAULT_RESULTAT);
        assertThat(testResultatActe.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testResultatActe.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testResultatActe.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testResultatActe.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testResultatActe.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testResultatActe.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testResultatActe.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createResultatActeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultatActeRepository.findAll().size();

        // Create the ResultatActe with an existing ID
        resultatActe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatActeMockMvc.perform(post("/api/resultat-actes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultatActe)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatActe in the database
        List<ResultatActe> resultatActeList = resultatActeRepository.findAll();
        assertThat(resultatActeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResultatActes() throws Exception {
        // Initialize the database
        resultatActeRepository.saveAndFlush(resultatActe);

        // Get all the resultatActeList
        restResultatActeMockMvc.perform(get("/api/resultat-actes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultatActe.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].resultat").value(hasItem(DEFAULT_RESULTAT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllResultatActesWithEagerRelationshipsIsEnabled() throws Exception {
        ResultatActeResource resultatActeResource = new ResultatActeResource(resultatActeServiceMock);
        when(resultatActeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restResultatActeMockMvc = MockMvcBuilders.standaloneSetup(resultatActeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResultatActeMockMvc.perform(get("/api/resultat-actes?eagerload=true"))
        .andExpect(status().isOk());

        verify(resultatActeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllResultatActesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ResultatActeResource resultatActeResource = new ResultatActeResource(resultatActeServiceMock);
            when(resultatActeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restResultatActeMockMvc = MockMvcBuilders.standaloneSetup(resultatActeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResultatActeMockMvc.perform(get("/api/resultat-actes?eagerload=true"))
        .andExpect(status().isOk());

            verify(resultatActeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getResultatActe() throws Exception {
        // Initialize the database
        resultatActeRepository.saveAndFlush(resultatActe);

        // Get the resultatActe
        restResultatActeMockMvc.perform(get("/api/resultat-actes/{id}", resultatActe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultatActe.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.resultat").value(DEFAULT_RESULTAT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResultatActe() throws Exception {
        // Get the resultatActe
        restResultatActeMockMvc.perform(get("/api/resultat-actes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultatActe() throws Exception {
        // Initialize the database
        resultatActeService.save(resultatActe);

        int databaseSizeBeforeUpdate = resultatActeRepository.findAll().size();

        // Update the resultatActe
        ResultatActe updatedResultatActe = resultatActeRepository.findById(resultatActe.getId()).get();
        // Disconnect from session so that the updates on updatedResultatActe are not directly saved in db
        em.detach(updatedResultatActe);
        updatedResultatActe
            .numero(UPDATED_NUMERO)
            .resultat(UPDATED_RESULTAT)
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restResultatActeMockMvc.perform(put("/api/resultat-actes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultatActe)))
            .andExpect(status().isOk());

        // Validate the ResultatActe in the database
        List<ResultatActe> resultatActeList = resultatActeRepository.findAll();
        assertThat(resultatActeList).hasSize(databaseSizeBeforeUpdate);
        ResultatActe testResultatActe = resultatActeList.get(resultatActeList.size() - 1);
        assertThat(testResultatActe.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testResultatActe.getResultat()).isEqualTo(UPDATED_RESULTAT);
        assertThat(testResultatActe.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testResultatActe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResultatActe.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testResultatActe.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testResultatActe.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testResultatActe.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testResultatActe.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingResultatActe() throws Exception {
        int databaseSizeBeforeUpdate = resultatActeRepository.findAll().size();

        // Create the ResultatActe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatActeMockMvc.perform(put("/api/resultat-actes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultatActe)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatActe in the database
        List<ResultatActe> resultatActeList = resultatActeRepository.findAll();
        assertThat(resultatActeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultatActe() throws Exception {
        // Initialize the database
        resultatActeService.save(resultatActe);

        int databaseSizeBeforeDelete = resultatActeRepository.findAll().size();

        // Delete the resultatActe
        restResultatActeMockMvc.perform(delete("/api/resultat-actes/{id}", resultatActe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultatActe> resultatActeList = resultatActeRepository.findAll();
        assertThat(resultatActeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
