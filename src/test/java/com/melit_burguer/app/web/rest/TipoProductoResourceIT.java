package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.MelitBurguerApp;
import com.melit_burguer.app.domain.TipoProducto;
import com.melit_burguer.app.repository.TipoProductoRepository;
import com.melit_burguer.app.service.TipoProductoService;
import com.melit_burguer.app.service.dto.TipoProductoDTO;
import com.melit_burguer.app.service.mapper.TipoProductoMapper;
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
 * Integration tests for the {@Link TipoProductoResource} REST controller.
 */
@SpringBootTest(classes = MelitBurguerApp.class)
public class TipoProductoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private TipoProductoMapper tipoProductoMapper;

    @Autowired
    private TipoProductoService tipoProductoService;

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

    private MockMvc restTipoProductoMockMvc;

    private TipoProducto tipoProducto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoProductoResource tipoProductoResource = new TipoProductoResource(tipoProductoService);
        this.restTipoProductoMockMvc = MockMvcBuilders.standaloneSetup(tipoProductoResource)
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
    public static TipoProducto createEntity(EntityManager em) {
        TipoProducto tipoProducto = new TipoProducto()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoProducto;
    }

    @BeforeEach
    public void initTest() {
        tipoProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoProducto() throws Exception {
        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();

        // Create the TipoProducto
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(tipoProducto);
        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();

        // Create the TipoProducto with an existing ID
        tipoProducto.setId(1L);
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(tipoProducto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoProductos() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get all the tipoProductoList
        restTipoProductoMockMvc.perform(get("/api/tipo-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get the tipoProducto
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/{id}", tipoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProducto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoProducto() throws Exception {
        // Get the tipoProducto
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Update the tipoProducto
        TipoProducto updatedTipoProducto = tipoProductoRepository.findById(tipoProducto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProducto are not directly saved in db
        em.detach(updatedTipoProducto);
        updatedTipoProducto
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(updatedTipoProducto);

        restTipoProductoMockMvc.perform(put("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Create the TipoProducto
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(tipoProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc.perform(put("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeDelete = tipoProductoRepository.findAll().size();

        // Delete the tipoProducto
        restTipoProductoMockMvc.perform(delete("/api/tipo-productos/{id}", tipoProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProducto.class);
        TipoProducto tipoProducto1 = new TipoProducto();
        tipoProducto1.setId(1L);
        TipoProducto tipoProducto2 = new TipoProducto();
        tipoProducto2.setId(tipoProducto1.getId());
        assertThat(tipoProducto1).isEqualTo(tipoProducto2);
        tipoProducto2.setId(2L);
        assertThat(tipoProducto1).isNotEqualTo(tipoProducto2);
        tipoProducto1.setId(null);
        assertThat(tipoProducto1).isNotEqualTo(tipoProducto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProductoDTO.class);
        TipoProductoDTO tipoProductoDTO1 = new TipoProductoDTO();
        tipoProductoDTO1.setId(1L);
        TipoProductoDTO tipoProductoDTO2 = new TipoProductoDTO();
        assertThat(tipoProductoDTO1).isNotEqualTo(tipoProductoDTO2);
        tipoProductoDTO2.setId(tipoProductoDTO1.getId());
        assertThat(tipoProductoDTO1).isEqualTo(tipoProductoDTO2);
        tipoProductoDTO2.setId(2L);
        assertThat(tipoProductoDTO1).isNotEqualTo(tipoProductoDTO2);
        tipoProductoDTO1.setId(null);
        assertThat(tipoProductoDTO1).isNotEqualTo(tipoProductoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoProductoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoProductoMapper.fromId(null)).isNull();
    }
}
