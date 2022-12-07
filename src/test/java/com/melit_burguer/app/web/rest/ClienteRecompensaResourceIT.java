package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.MelitBurguerApp;
import com.melit_burguer.app.domain.ClienteRecompensa;
import com.melit_burguer.app.repository.ClienteRecompensaRepository;
import com.melit_burguer.app.service.ClienteRecompensaService;
import com.melit_burguer.app.service.dto.ClienteRecompensaDTO;
import com.melit_burguer.app.service.mapper.ClienteRecompensaMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.melit_burguer.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ClienteRecompensaResource} REST controller.
 */
@SpringBootTest(classes = MelitBurguerApp.class)
public class ClienteRecompensaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ClienteRecompensaRepository clienteRecompensaRepository;

    @Autowired
    private ClienteRecompensaMapper clienteRecompensaMapper;

    @Autowired
    private ClienteRecompensaService clienteRecompensaService;

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

    private MockMvc restClienteRecompensaMockMvc;

    private ClienteRecompensa clienteRecompensa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteRecompensaResource clienteRecompensaResource = new ClienteRecompensaResource(clienteRecompensaService);
        this.restClienteRecompensaMockMvc = MockMvcBuilders.standaloneSetup(clienteRecompensaResource)
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
    public static ClienteRecompensa createEntity(EntityManager em) {
        ClienteRecompensa clienteRecompensa = new ClienteRecompensa()
            .fecha(DEFAULT_FECHA);
        return clienteRecompensa;
    }

    @BeforeEach
    public void initTest() {
        clienteRecompensa = createEntity(em);
    }

    @Test
    @Transactional
    public void createClienteRecompensa() throws Exception {
        int databaseSizeBeforeCreate = clienteRecompensaRepository.findAll().size();

        // Create the ClienteRecompensa
        ClienteRecompensaDTO clienteRecompensaDTO = clienteRecompensaMapper.toDto(clienteRecompensa);
        restClienteRecompensaMockMvc.perform(post("/api/cliente-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteRecompensaDTO)))
            .andExpect(status().isCreated());

        // Validate the ClienteRecompensa in the database
        List<ClienteRecompensa> clienteRecompensaList = clienteRecompensaRepository.findAll();
        assertThat(clienteRecompensaList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteRecompensa testClienteRecompensa = clienteRecompensaList.get(clienteRecompensaList.size() - 1);
        assertThat(testClienteRecompensa.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createClienteRecompensaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRecompensaRepository.findAll().size();

        // Create the ClienteRecompensa with an existing ID
        clienteRecompensa.setId(1L);
        ClienteRecompensaDTO clienteRecompensaDTO = clienteRecompensaMapper.toDto(clienteRecompensa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteRecompensaMockMvc.perform(post("/api/cliente-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteRecompensaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteRecompensa in the database
        List<ClienteRecompensa> clienteRecompensaList = clienteRecompensaRepository.findAll();
        assertThat(clienteRecompensaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClienteRecompensas() throws Exception {
        // Initialize the database
        clienteRecompensaRepository.saveAndFlush(clienteRecompensa);

        // Get all the clienteRecompensaList
        restClienteRecompensaMockMvc.perform(get("/api/cliente-recompensas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteRecompensa.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getClienteRecompensa() throws Exception {
        // Initialize the database
        clienteRecompensaRepository.saveAndFlush(clienteRecompensa);

        // Get the clienteRecompensa
        restClienteRecompensaMockMvc.perform(get("/api/cliente-recompensas/{id}", clienteRecompensa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clienteRecompensa.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClienteRecompensa() throws Exception {
        // Get the clienteRecompensa
        restClienteRecompensaMockMvc.perform(get("/api/cliente-recompensas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClienteRecompensa() throws Exception {
        // Initialize the database
        clienteRecompensaRepository.saveAndFlush(clienteRecompensa);

        int databaseSizeBeforeUpdate = clienteRecompensaRepository.findAll().size();

        // Update the clienteRecompensa
        ClienteRecompensa updatedClienteRecompensa = clienteRecompensaRepository.findById(clienteRecompensa.getId()).get();
        // Disconnect from session so that the updates on updatedClienteRecompensa are not directly saved in db
        em.detach(updatedClienteRecompensa);
        updatedClienteRecompensa
            .fecha(UPDATED_FECHA);
        ClienteRecompensaDTO clienteRecompensaDTO = clienteRecompensaMapper.toDto(updatedClienteRecompensa);

        restClienteRecompensaMockMvc.perform(put("/api/cliente-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteRecompensaDTO)))
            .andExpect(status().isOk());

        // Validate the ClienteRecompensa in the database
        List<ClienteRecompensa> clienteRecompensaList = clienteRecompensaRepository.findAll();
        assertThat(clienteRecompensaList).hasSize(databaseSizeBeforeUpdate);
        ClienteRecompensa testClienteRecompensa = clienteRecompensaList.get(clienteRecompensaList.size() - 1);
        assertThat(testClienteRecompensa.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingClienteRecompensa() throws Exception {
        int databaseSizeBeforeUpdate = clienteRecompensaRepository.findAll().size();

        // Create the ClienteRecompensa
        ClienteRecompensaDTO clienteRecompensaDTO = clienteRecompensaMapper.toDto(clienteRecompensa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteRecompensaMockMvc.perform(put("/api/cliente-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteRecompensaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteRecompensa in the database
        List<ClienteRecompensa> clienteRecompensaList = clienteRecompensaRepository.findAll();
        assertThat(clienteRecompensaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClienteRecompensa() throws Exception {
        // Initialize the database
        clienteRecompensaRepository.saveAndFlush(clienteRecompensa);

        int databaseSizeBeforeDelete = clienteRecompensaRepository.findAll().size();

        // Delete the clienteRecompensa
        restClienteRecompensaMockMvc.perform(delete("/api/cliente-recompensas/{id}", clienteRecompensa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ClienteRecompensa> clienteRecompensaList = clienteRecompensaRepository.findAll();
        assertThat(clienteRecompensaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteRecompensa.class);
        ClienteRecompensa clienteRecompensa1 = new ClienteRecompensa();
        clienteRecompensa1.setId(1L);
        ClienteRecompensa clienteRecompensa2 = new ClienteRecompensa();
        clienteRecompensa2.setId(clienteRecompensa1.getId());
        assertThat(clienteRecompensa1).isEqualTo(clienteRecompensa2);
        clienteRecompensa2.setId(2L);
        assertThat(clienteRecompensa1).isNotEqualTo(clienteRecompensa2);
        clienteRecompensa1.setId(null);
        assertThat(clienteRecompensa1).isNotEqualTo(clienteRecompensa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteRecompensaDTO.class);
        ClienteRecompensaDTO clienteRecompensaDTO1 = new ClienteRecompensaDTO();
        clienteRecompensaDTO1.setId(1L);
        ClienteRecompensaDTO clienteRecompensaDTO2 = new ClienteRecompensaDTO();
        assertThat(clienteRecompensaDTO1).isNotEqualTo(clienteRecompensaDTO2);
        clienteRecompensaDTO2.setId(clienteRecompensaDTO1.getId());
        assertThat(clienteRecompensaDTO1).isEqualTo(clienteRecompensaDTO2);
        clienteRecompensaDTO2.setId(2L);
        assertThat(clienteRecompensaDTO1).isNotEqualTo(clienteRecompensaDTO2);
        clienteRecompensaDTO1.setId(null);
        assertThat(clienteRecompensaDTO1).isNotEqualTo(clienteRecompensaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteRecompensaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteRecompensaMapper.fromId(null)).isNull();
    }
}
