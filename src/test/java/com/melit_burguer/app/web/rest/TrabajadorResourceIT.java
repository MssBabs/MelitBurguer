package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.MelitBurguerApp;
import com.melit_burguer.app.domain.Trabajador;
import com.melit_burguer.app.repository.TrabajadorRepository;
import com.melit_burguer.app.service.TrabajadorService;
import com.melit_burguer.app.service.dto.TrabajadorDTO;
import com.melit_burguer.app.service.mapper.TrabajadorMapper;
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
 * Integration tests for the {@Link TrabajadorResource} REST controller.
 */
@SpringBootTest(classes = MelitBurguerApp.class)
public class TrabajadorResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private TrabajadorMapper trabajadorMapper;

    @Autowired
    private TrabajadorService trabajadorService;

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

    private MockMvc restTrabajadorMockMvc;

    private Trabajador trabajador;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrabajadorResource trabajadorResource = new TrabajadorResource(trabajadorService);
        this.restTrabajadorMockMvc = MockMvcBuilders.standaloneSetup(trabajadorResource)
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
    public static Trabajador createEntity(EntityManager em) {
        Trabajador trabajador = new Trabajador()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .dni(DEFAULT_DNI)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO);
        return trabajador;
    }

    @BeforeEach
    public void initTest() {
        trabajador = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrabajador() throws Exception {
        int databaseSizeBeforeCreate = trabajadorRepository.findAll().size();

        // Create the Trabajador
        TrabajadorDTO trabajadorDTO = trabajadorMapper.toDto(trabajador);
        restTrabajadorMockMvc.perform(post("/api/trabajadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajadorDTO)))
            .andExpect(status().isCreated());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeCreate + 1);
        Trabajador testTrabajador = trabajadorList.get(trabajadorList.size() - 1);
        assertThat(testTrabajador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTrabajador.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testTrabajador.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testTrabajador.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testTrabajador.getCorreo()).isEqualTo(DEFAULT_CORREO);
    }

    @Test
    @Transactional
    public void createTrabajadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trabajadorRepository.findAll().size();

        // Create the Trabajador with an existing ID
        trabajador.setId(1L);
        TrabajadorDTO trabajadorDTO = trabajadorMapper.toDto(trabajador);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrabajadorMockMvc.perform(post("/api/trabajadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTrabajadors() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        // Get all the trabajadorList
        restTrabajadorMockMvc.perform(get("/api/trabajadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabajador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())));
    }
    
    @Test
    @Transactional
    public void getTrabajador() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        // Get the trabajador
        restTrabajadorMockMvc.perform(get("/api/trabajadors/{id}", trabajador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trabajador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrabajador() throws Exception {
        // Get the trabajador
        restTrabajadorMockMvc.perform(get("/api/trabajadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrabajador() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();

        // Update the trabajador
        Trabajador updatedTrabajador = trabajadorRepository.findById(trabajador.getId()).get();
        // Disconnect from session so that the updates on updatedTrabajador are not directly saved in db
        em.detach(updatedTrabajador);
        updatedTrabajador
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .dni(UPDATED_DNI)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO);
        TrabajadorDTO trabajadorDTO = trabajadorMapper.toDto(updatedTrabajador);

        restTrabajadorMockMvc.perform(put("/api/trabajadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajadorDTO)))
            .andExpect(status().isOk());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
        Trabajador testTrabajador = trabajadorList.get(trabajadorList.size() - 1);
        assertThat(testTrabajador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTrabajador.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testTrabajador.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testTrabajador.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testTrabajador.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void updateNonExistingTrabajador() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();

        // Create the Trabajador
        TrabajadorDTO trabajadorDTO = trabajadorMapper.toDto(trabajador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrabajadorMockMvc.perform(put("/api/trabajadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrabajador() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        int databaseSizeBeforeDelete = trabajadorRepository.findAll().size();

        // Delete the trabajador
        restTrabajadorMockMvc.perform(delete("/api/trabajadors/{id}", trabajador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trabajador.class);
        Trabajador trabajador1 = new Trabajador();
        trabajador1.setId(1L);
        Trabajador trabajador2 = new Trabajador();
        trabajador2.setId(trabajador1.getId());
        assertThat(trabajador1).isEqualTo(trabajador2);
        trabajador2.setId(2L);
        assertThat(trabajador1).isNotEqualTo(trabajador2);
        trabajador1.setId(null);
        assertThat(trabajador1).isNotEqualTo(trabajador2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrabajadorDTO.class);
        TrabajadorDTO trabajadorDTO1 = new TrabajadorDTO();
        trabajadorDTO1.setId(1L);
        TrabajadorDTO trabajadorDTO2 = new TrabajadorDTO();
        assertThat(trabajadorDTO1).isNotEqualTo(trabajadorDTO2);
        trabajadorDTO2.setId(trabajadorDTO1.getId());
        assertThat(trabajadorDTO1).isEqualTo(trabajadorDTO2);
        trabajadorDTO2.setId(2L);
        assertThat(trabajadorDTO1).isNotEqualTo(trabajadorDTO2);
        trabajadorDTO1.setId(null);
        assertThat(trabajadorDTO1).isNotEqualTo(trabajadorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trabajadorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trabajadorMapper.fromId(null)).isNull();
    }
}
