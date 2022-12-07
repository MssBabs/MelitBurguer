package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.MelitBurguerApp;
import com.melit_burguer.app.domain.EstadoPedido;
import com.melit_burguer.app.repository.EstadoPedidoRepository;
import com.melit_burguer.app.service.EstadoPedidoService;
import com.melit_burguer.app.service.dto.EstadoPedidoDTO;
import com.melit_burguer.app.service.mapper.EstadoPedidoMapper;
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
 * Integration tests for the {@Link EstadoPedidoResource} REST controller.
 */
@SpringBootTest(classes = MelitBurguerApp.class)
public class EstadoPedidoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private EstadoPedidoMapper estadoPedidoMapper;

    @Autowired
    private EstadoPedidoService estadoPedidoService;

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

    private MockMvc restEstadoPedidoMockMvc;

    private EstadoPedido estadoPedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadoPedidoResource estadoPedidoResource = new EstadoPedidoResource(estadoPedidoService);
        this.restEstadoPedidoMockMvc = MockMvcBuilders.standaloneSetup(estadoPedidoResource)
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
    public static EstadoPedido createEntity(EntityManager em) {
        EstadoPedido estadoPedido = new EstadoPedido()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return estadoPedido;
    }

    @BeforeEach
    public void initTest() {
        estadoPedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoPedido() throws Exception {
        int databaseSizeBeforeCreate = estadoPedidoRepository.findAll().size();

        // Create the EstadoPedido
        EstadoPedidoDTO estadoPedidoDTO = estadoPedidoMapper.toDto(estadoPedido);
        restEstadoPedidoMockMvc.perform(post("/api/estado-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoPedidoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoPedido testEstadoPedido = estadoPedidoList.get(estadoPedidoList.size() - 1);
        assertThat(testEstadoPedido.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEstadoPedido.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createEstadoPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoPedidoRepository.findAll().size();

        // Create the EstadoPedido with an existing ID
        estadoPedido.setId(1L);
        EstadoPedidoDTO estadoPedidoDTO = estadoPedidoMapper.toDto(estadoPedido);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoPedidoMockMvc.perform(post("/api/estado-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoPedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstadoPedidos() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        // Get all the estadoPedidoList
        restEstadoPedidoMockMvc.perform(get("/api/estado-pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getEstadoPedido() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        // Get the estadoPedido
        restEstadoPedidoMockMvc.perform(get("/api/estado-pedidos/{id}", estadoPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estadoPedido.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoPedido() throws Exception {
        // Get the estadoPedido
        restEstadoPedidoMockMvc.perform(get("/api/estado-pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoPedido() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();

        // Update the estadoPedido
        EstadoPedido updatedEstadoPedido = estadoPedidoRepository.findById(estadoPedido.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoPedido are not directly saved in db
        em.detach(updatedEstadoPedido);
        updatedEstadoPedido
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);
        EstadoPedidoDTO estadoPedidoDTO = estadoPedidoMapper.toDto(updatedEstadoPedido);

        restEstadoPedidoMockMvc.perform(put("/api/estado-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoPedidoDTO)))
            .andExpect(status().isOk());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
        EstadoPedido testEstadoPedido = estadoPedidoList.get(estadoPedidoList.size() - 1);
        assertThat(testEstadoPedido.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstadoPedido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoPedido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();

        // Create the EstadoPedido
        EstadoPedidoDTO estadoPedidoDTO = estadoPedidoMapper.toDto(estadoPedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoPedidoMockMvc.perform(put("/api/estado-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estadoPedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstadoPedido() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        int databaseSizeBeforeDelete = estadoPedidoRepository.findAll().size();

        // Delete the estadoPedido
        restEstadoPedidoMockMvc.perform(delete("/api/estado-pedidos/{id}", estadoPedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoPedido.class);
        EstadoPedido estadoPedido1 = new EstadoPedido();
        estadoPedido1.setId(1L);
        EstadoPedido estadoPedido2 = new EstadoPedido();
        estadoPedido2.setId(estadoPedido1.getId());
        assertThat(estadoPedido1).isEqualTo(estadoPedido2);
        estadoPedido2.setId(2L);
        assertThat(estadoPedido1).isNotEqualTo(estadoPedido2);
        estadoPedido1.setId(null);
        assertThat(estadoPedido1).isNotEqualTo(estadoPedido2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoPedidoDTO.class);
        EstadoPedidoDTO estadoPedidoDTO1 = new EstadoPedidoDTO();
        estadoPedidoDTO1.setId(1L);
        EstadoPedidoDTO estadoPedidoDTO2 = new EstadoPedidoDTO();
        assertThat(estadoPedidoDTO1).isNotEqualTo(estadoPedidoDTO2);
        estadoPedidoDTO2.setId(estadoPedidoDTO1.getId());
        assertThat(estadoPedidoDTO1).isEqualTo(estadoPedidoDTO2);
        estadoPedidoDTO2.setId(2L);
        assertThat(estadoPedidoDTO1).isNotEqualTo(estadoPedidoDTO2);
        estadoPedidoDTO1.setId(null);
        assertThat(estadoPedidoDTO1).isNotEqualTo(estadoPedidoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estadoPedidoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estadoPedidoMapper.fromId(null)).isNull();
    }
}
