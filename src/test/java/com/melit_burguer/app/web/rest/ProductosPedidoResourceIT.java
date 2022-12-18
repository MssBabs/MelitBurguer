package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.MelitBurguerApp;
import com.melit_burguer.app.domain.ProductosPedido;
import com.melit_burguer.app.repository.ProductosPedidoRepository;
import com.melit_burguer.app.service.ProductosPedidoService;
import com.melit_burguer.app.service.dto.ProductosPedidoDTO;
import com.melit_burguer.app.service.mapper.ProductosPedidoMapper;
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
 * Integration tests for the {@Link ProductosPedidoResource} REST controller.
 */
@SpringBootTest(classes = MelitBurguerApp.class)
public class ProductosPedidoResourceIT {

    private static final Double DEFAULT_PRECIO_TOTAL = 1D;
    private static final Double UPDATED_PRECIO_TOTAL = 2D;

    @Autowired
    private ProductosPedidoRepository productosPedidoRepository;

    @Autowired
    private ProductosPedidoMapper productosPedidoMapper;

    @Autowired
    private ProductosPedidoService productosPedidoService;

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

    private MockMvc restProductosPedidoMockMvc;

    private ProductosPedido productosPedido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductosPedidoResource productosPedidoResource = new ProductosPedidoResource(productosPedidoService);
        this.restProductosPedidoMockMvc = MockMvcBuilders.standaloneSetup(productosPedidoResource)
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
    public static ProductosPedido createEntity(EntityManager em) {
        ProductosPedido productosPedido = new ProductosPedido()
            .precio(DEFAULT_PRECIO_TOTAL);
        return productosPedido;
    }

    @BeforeEach
    public void initTest() {
        productosPedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductosPedido() throws Exception {
        int databaseSizeBeforeCreate = productosPedidoRepository.findAll().size();

        // Create the ProductosPedido
        ProductosPedidoDTO productosPedidoDTO = productosPedidoMapper.toDto(productosPedido);
        restProductosPedidoMockMvc.perform(post("/api/productos-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosPedidoDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductosPedido in the database
        List<ProductosPedido> productosPedidoList = productosPedidoRepository.findAll();
        assertThat(productosPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductosPedido testProductosPedido = productosPedidoList.get(productosPedidoList.size() - 1);
        assertThat(testProductosPedido.getPrecio()).isEqualTo(DEFAULT_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    public void createProductosPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productosPedidoRepository.findAll().size();

        // Create the ProductosPedido with an existing ID
        productosPedido.setId(1L);
        ProductosPedidoDTO productosPedidoDTO = productosPedidoMapper.toDto(productosPedido);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductosPedidoMockMvc.perform(post("/api/productos-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosPedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductosPedido in the database
        List<ProductosPedido> productosPedidoList = productosPedidoRepository.findAll();
        assertThat(productosPedidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductosPedidos() throws Exception {
        // Initialize the database
        productosPedidoRepository.saveAndFlush(productosPedido);

        // Get all the productosPedidoList
        restProductosPedidoMockMvc.perform(get("/api/productos-pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productosPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.doubleValue())));
    }

    @Test
    @Transactional
    public void getProductosPedido() throws Exception {
        // Initialize the database
        productosPedidoRepository.saveAndFlush(productosPedido);

        // Get the productosPedido
        restProductosPedidoMockMvc.perform(get("/api/productos-pedidos/{id}", productosPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productosPedido.getId().intValue()))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductosPedido() throws Exception {
        // Get the productosPedido
        restProductosPedidoMockMvc.perform(get("/api/productos-pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductosPedido() throws Exception {
        // Initialize the database
        productosPedidoRepository.saveAndFlush(productosPedido);

        int databaseSizeBeforeUpdate = productosPedidoRepository.findAll().size();

        // Update the productosPedido
        ProductosPedido updatedProductosPedido = productosPedidoRepository.findById(productosPedido.getId()).get();
        // Disconnect from session so that the updates on updatedProductosPedido are not directly saved in db
        em.detach(updatedProductosPedido);
        updatedProductosPedido
            .precio(UPDATED_PRECIO_TOTAL);
        ProductosPedidoDTO productosPedidoDTO = productosPedidoMapper.toDto(updatedProductosPedido);

        restProductosPedidoMockMvc.perform(put("/api/productos-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosPedidoDTO)))
            .andExpect(status().isOk());

        // Validate the ProductosPedido in the database
        List<ProductosPedido> productosPedidoList = productosPedidoRepository.findAll();
        assertThat(productosPedidoList).hasSize(databaseSizeBeforeUpdate);
        ProductosPedido testProductosPedido = productosPedidoList.get(productosPedidoList.size() - 1);
        assertThat(testProductosPedido.getPrecio()).isEqualTo(UPDATED_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingProductosPedido() throws Exception {
        int databaseSizeBeforeUpdate = productosPedidoRepository.findAll().size();

        // Create the ProductosPedido
        ProductosPedidoDTO productosPedidoDTO = productosPedidoMapper.toDto(productosPedido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductosPedidoMockMvc.perform(put("/api/productos-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosPedidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductosPedido in the database
        List<ProductosPedido> productosPedidoList = productosPedidoRepository.findAll();
        assertThat(productosPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductosPedido() throws Exception {
        // Initialize the database
        productosPedidoRepository.saveAndFlush(productosPedido);

        int databaseSizeBeforeDelete = productosPedidoRepository.findAll().size();

        // Delete the productosPedido
        restProductosPedidoMockMvc.perform(delete("/api/productos-pedidos/{id}", productosPedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProductosPedido> productosPedidoList = productosPedidoRepository.findAll();
        assertThat(productosPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductosPedido.class);
        ProductosPedido productosPedido1 = new ProductosPedido();
        productosPedido1.setId(1L);
        ProductosPedido productosPedido2 = new ProductosPedido();
        productosPedido2.setId(productosPedido1.getId());
        assertThat(productosPedido1).isEqualTo(productosPedido2);
        productosPedido2.setId(2L);
        assertThat(productosPedido1).isNotEqualTo(productosPedido2);
        productosPedido1.setId(null);
        assertThat(productosPedido1).isNotEqualTo(productosPedido2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductosPedidoDTO.class);
        ProductosPedidoDTO productosPedidoDTO1 = new ProductosPedidoDTO();
        productosPedidoDTO1.setId(1L);
        ProductosPedidoDTO productosPedidoDTO2 = new ProductosPedidoDTO();
        assertThat(productosPedidoDTO1).isNotEqualTo(productosPedidoDTO2);
        productosPedidoDTO2.setId(productosPedidoDTO1.getId());
        assertThat(productosPedidoDTO1).isEqualTo(productosPedidoDTO2);
        productosPedidoDTO2.setId(2L);
        assertThat(productosPedidoDTO1).isNotEqualTo(productosPedidoDTO2);
        productosPedidoDTO1.setId(null);
        assertThat(productosPedidoDTO1).isNotEqualTo(productosPedidoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productosPedidoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productosPedidoMapper.fromId(null)).isNull();
    }
}
