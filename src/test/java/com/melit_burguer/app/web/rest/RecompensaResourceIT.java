package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.MelitBurguerApp;
import com.melit_burguer.app.domain.Recompensa;
import com.melit_burguer.app.repository.RecompensaRepository;
import com.melit_burguer.app.service.RecompensaService;
import com.melit_burguer.app.service.dto.RecompensaDTO;
import com.melit_burguer.app.service.mapper.RecompensaMapper;
import com.melit_burguer.app.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.melit_burguer.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RecompensaResource} REST controller.
 */
@SpringBootTest(classes = MelitBurguerApp.class)
public class RecompensaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUNTOS = 1;
    private static final Integer UPDATED_PUNTOS = 2;

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Autowired
    private RecompensaMapper recompensaMapper;

    @Autowired
    private RecompensaService recompensaService;

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

    private MockMvc restRecompensaMockMvc;

    private Recompensa recompensa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecompensaResource recompensaResource = new RecompensaResource(recompensaService);
        this.restRecompensaMockMvc = MockMvcBuilders.standaloneSetup(recompensaResource)
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
    public static Recompensa createEntity(EntityManager em) {
        Recompensa recompensa = new Recompensa()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .puntos(DEFAULT_PUNTOS);
        return recompensa;
    }

    @BeforeEach
    public void initTest() {
        recompensa = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecompensa() throws Exception {
        int databaseSizeBeforeCreate = recompensaRepository.findAll().size();

        // Create the Recompensa
        RecompensaDTO recompensaDTO = recompensaMapper.toDto(recompensa);
        restRecompensaMockMvc.perform(post("/api/recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recompensaDTO)))
            .andExpect(status().isCreated());

        // Validate the Recompensa in the database
        List<Recompensa> recompensaList = recompensaRepository.findAll();
        assertThat(recompensaList).hasSize(databaseSizeBeforeCreate + 1);
        Recompensa testRecompensa = recompensaList.get(recompensaList.size() - 1);
        assertThat(testRecompensa.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRecompensa.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRecompensa.getPuntos()).isEqualTo(DEFAULT_PUNTOS);
    }

    @Test
    @Transactional
    public void createRecompensaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recompensaRepository.findAll().size();

        // Create the Recompensa with an existing ID
        recompensa.setId(1L);
        RecompensaDTO recompensaDTO = recompensaMapper.toDto(recompensa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecompensaMockMvc.perform(post("/api/recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recompensaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recompensa in the database
        List<Recompensa> recompensaList = recompensaRepository.findAll();
        assertThat(recompensaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecompensas() throws Exception {
        // Initialize the database
        recompensaRepository.saveAndFlush(recompensa);

        // Get all the recompensaList
        restRecompensaMockMvc.perform(get("/api/recompensas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recompensa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].puntos").value(hasItem(DEFAULT_PUNTOS)));
    }
    
    @Test
    @Transactional
    public void getRecompensa() throws Exception {
        // Initialize the database
        recompensaRepository.saveAndFlush(recompensa);

        // Get the recompensa
        restRecompensaMockMvc.perform(get("/api/recompensas/{id}", recompensa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recompensa.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.puntos").value(DEFAULT_PUNTOS));
    }

    @Test
    @Transactional
    public void getNonExistingRecompensa() throws Exception {
        // Get the recompensa
        restRecompensaMockMvc.perform(get("/api/recompensas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecompensa() throws Exception {
        // Initialize the database
        recompensaRepository.saveAndFlush(recompensa);

        int databaseSizeBeforeUpdate = recompensaRepository.findAll().size();

        // Update the recompensa
        Recompensa updatedRecompensa = recompensaRepository.findById(recompensa.getId()).get();
        // Disconnect from session so that the updates on updatedRecompensa are not directly saved in db
        em.detach(updatedRecompensa);
        updatedRecompensa
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .puntos(UPDATED_PUNTOS);
        RecompensaDTO recompensaDTO = recompensaMapper.toDto(updatedRecompensa);

        restRecompensaMockMvc.perform(put("/api/recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recompensaDTO)))
            .andExpect(status().isOk());

        // Validate the Recompensa in the database
        List<Recompensa> recompensaList = recompensaRepository.findAll();
        assertThat(recompensaList).hasSize(databaseSizeBeforeUpdate);
        Recompensa testRecompensa = recompensaList.get(recompensaList.size() - 1);
        assertThat(testRecompensa.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRecompensa.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRecompensa.getPuntos()).isEqualTo(UPDATED_PUNTOS);
    }

    @Test
    @Transactional
    public void updateNonExistingRecompensa() throws Exception {
        int databaseSizeBeforeUpdate = recompensaRepository.findAll().size();

        // Create the Recompensa
        RecompensaDTO recompensaDTO = recompensaMapper.toDto(recompensa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecompensaMockMvc.perform(put("/api/recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recompensaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recompensa in the database
        List<Recompensa> recompensaList = recompensaRepository.findAll();
        assertThat(recompensaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecompensa() throws Exception {
        // Initialize the database
        recompensaRepository.saveAndFlush(recompensa);

        int databaseSizeBeforeDelete = recompensaRepository.findAll().size();

        // Delete the recompensa
        restRecompensaMockMvc.perform(delete("/api/recompensas/{id}", recompensa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Recompensa> recompensaList = recompensaRepository.findAll();
        assertThat(recompensaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recompensa.class);
        Recompensa recompensa1 = new Recompensa();
        recompensa1.setId(1L);
        Recompensa recompensa2 = new Recompensa();
        recompensa2.setId(recompensa1.getId());
        assertThat(recompensa1).isEqualTo(recompensa2);
        recompensa2.setId(2L);
        assertThat(recompensa1).isNotEqualTo(recompensa2);
        recompensa1.setId(null);
        assertThat(recompensa1).isNotEqualTo(recompensa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecompensaDTO.class);
        RecompensaDTO recompensaDTO1 = new RecompensaDTO();
        recompensaDTO1.setId(1L);
        RecompensaDTO recompensaDTO2 = new RecompensaDTO();
        assertThat(recompensaDTO1).isNotEqualTo(recompensaDTO2);
        recompensaDTO2.setId(recompensaDTO1.getId());
        assertThat(recompensaDTO1).isEqualTo(recompensaDTO2);
        recompensaDTO2.setId(2L);
        assertThat(recompensaDTO1).isNotEqualTo(recompensaDTO2);
        recompensaDTO1.setId(null);
        assertThat(recompensaDTO1).isNotEqualTo(recompensaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recompensaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recompensaMapper.fromId(null)).isNull();
    }
}
