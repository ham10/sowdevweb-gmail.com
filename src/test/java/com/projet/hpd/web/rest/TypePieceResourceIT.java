package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypePiece;
import com.projet.hpd.repository.TypePieceRepository;
import com.projet.hpd.service.TypePieceService;
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
 * Integration tests for the {@link TypePieceResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypePieceResourceIT {

    private static final String DEFAULT_CODE_TYPE_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_PIECE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_PIECE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_TYPE_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_TYPE_PIECE = "BBBBBBBBBB";

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
    private TypePieceRepository typePieceRepository;

    @Autowired
    private TypePieceService typePieceService;

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

    private MockMvc restTypePieceMockMvc;

    private TypePiece typePiece;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePieceResource typePieceResource = new TypePieceResource(typePieceService);
        this.restTypePieceMockMvc = MockMvcBuilders.standaloneSetup(typePieceResource)
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
    public static TypePiece createEntity(EntityManager em) {
        TypePiece typePiece = new TypePiece()
            .codeTypePiece(DEFAULT_CODE_TYPE_PIECE)
            .libelleTypePiece(DEFAULT_LIBELLE_TYPE_PIECE)
            .descriptionTypePiece(DEFAULT_DESCRIPTION_TYPE_PIECE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typePiece;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypePiece createUpdatedEntity(EntityManager em) {
        TypePiece typePiece = new TypePiece()
            .codeTypePiece(UPDATED_CODE_TYPE_PIECE)
            .libelleTypePiece(UPDATED_LIBELLE_TYPE_PIECE)
            .descriptionTypePiece(UPDATED_DESCRIPTION_TYPE_PIECE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typePiece;
    }

    @BeforeEach
    public void initTest() {
        typePiece = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePiece() throws Exception {
        int databaseSizeBeforeCreate = typePieceRepository.findAll().size();

        // Create the TypePiece
        restTypePieceMockMvc.perform(post("/api/type-pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePiece)))
            .andExpect(status().isCreated());

        // Validate the TypePiece in the database
        List<TypePiece> typePieceList = typePieceRepository.findAll();
        assertThat(typePieceList).hasSize(databaseSizeBeforeCreate + 1);
        TypePiece testTypePiece = typePieceList.get(typePieceList.size() - 1);
        assertThat(testTypePiece.getCodeTypePiece()).isEqualTo(DEFAULT_CODE_TYPE_PIECE);
        assertThat(testTypePiece.getLibelleTypePiece()).isEqualTo(DEFAULT_LIBELLE_TYPE_PIECE);
        assertThat(testTypePiece.getDescriptionTypePiece()).isEqualTo(DEFAULT_DESCRIPTION_TYPE_PIECE);
        assertThat(testTypePiece.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypePiece.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypePiece.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypePiece.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypePiece.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypePieceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePieceRepository.findAll().size();

        // Create the TypePiece with an existing ID
        typePiece.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePieceMockMvc.perform(post("/api/type-pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePiece)))
            .andExpect(status().isBadRequest());

        // Validate the TypePiece in the database
        List<TypePiece> typePieceList = typePieceRepository.findAll();
        assertThat(typePieceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypePieces() throws Exception {
        // Initialize the database
        typePieceRepository.saveAndFlush(typePiece);

        // Get all the typePieceList
        restTypePieceMockMvc.perform(get("/api/type-pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePiece.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypePiece").value(hasItem(DEFAULT_CODE_TYPE_PIECE)))
            .andExpect(jsonPath("$.[*].libelleTypePiece").value(hasItem(DEFAULT_LIBELLE_TYPE_PIECE)))
            .andExpect(jsonPath("$.[*].descriptionTypePiece").value(hasItem(DEFAULT_DESCRIPTION_TYPE_PIECE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypePiece() throws Exception {
        // Initialize the database
        typePieceRepository.saveAndFlush(typePiece);

        // Get the typePiece
        restTypePieceMockMvc.perform(get("/api/type-pieces/{id}", typePiece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typePiece.getId().intValue()))
            .andExpect(jsonPath("$.codeTypePiece").value(DEFAULT_CODE_TYPE_PIECE))
            .andExpect(jsonPath("$.libelleTypePiece").value(DEFAULT_LIBELLE_TYPE_PIECE))
            .andExpect(jsonPath("$.descriptionTypePiece").value(DEFAULT_DESCRIPTION_TYPE_PIECE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypePiece() throws Exception {
        // Get the typePiece
        restTypePieceMockMvc.perform(get("/api/type-pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePiece() throws Exception {
        // Initialize the database
        typePieceService.save(typePiece);

        int databaseSizeBeforeUpdate = typePieceRepository.findAll().size();

        // Update the typePiece
        TypePiece updatedTypePiece = typePieceRepository.findById(typePiece.getId()).get();
        // Disconnect from session so that the updates on updatedTypePiece are not directly saved in db
        em.detach(updatedTypePiece);
        updatedTypePiece
            .codeTypePiece(UPDATED_CODE_TYPE_PIECE)
            .libelleTypePiece(UPDATED_LIBELLE_TYPE_PIECE)
            .descriptionTypePiece(UPDATED_DESCRIPTION_TYPE_PIECE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypePieceMockMvc.perform(put("/api/type-pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePiece)))
            .andExpect(status().isOk());

        // Validate the TypePiece in the database
        List<TypePiece> typePieceList = typePieceRepository.findAll();
        assertThat(typePieceList).hasSize(databaseSizeBeforeUpdate);
        TypePiece testTypePiece = typePieceList.get(typePieceList.size() - 1);
        assertThat(testTypePiece.getCodeTypePiece()).isEqualTo(UPDATED_CODE_TYPE_PIECE);
        assertThat(testTypePiece.getLibelleTypePiece()).isEqualTo(UPDATED_LIBELLE_TYPE_PIECE);
        assertThat(testTypePiece.getDescriptionTypePiece()).isEqualTo(UPDATED_DESCRIPTION_TYPE_PIECE);
        assertThat(testTypePiece.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypePiece.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypePiece.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypePiece.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypePiece.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePiece() throws Exception {
        int databaseSizeBeforeUpdate = typePieceRepository.findAll().size();

        // Create the TypePiece

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypePieceMockMvc.perform(put("/api/type-pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typePiece)))
            .andExpect(status().isBadRequest());

        // Validate the TypePiece in the database
        List<TypePiece> typePieceList = typePieceRepository.findAll();
        assertThat(typePieceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePiece() throws Exception {
        // Initialize the database
        typePieceService.save(typePiece);

        int databaseSizeBeforeDelete = typePieceRepository.findAll().size();

        // Delete the typePiece
        restTypePieceMockMvc.perform(delete("/api/type-pieces/{id}", typePiece.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypePiece> typePieceList = typePieceRepository.findAll();
        assertThat(typePieceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
