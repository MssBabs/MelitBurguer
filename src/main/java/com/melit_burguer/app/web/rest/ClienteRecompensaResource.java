package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.service.ClienteRecompensaService;
import com.melit_burguer.app.web.rest.errors.BadRequestAlertException;
import com.melit_burguer.app.service.dto.ClienteRecompensaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.melit_burguer.app.domain.ClienteRecompensa}.
 */
@RestController
@RequestMapping("/api")
public class ClienteRecompensaResource {

    private final Logger log = LoggerFactory.getLogger(ClienteRecompensaResource.class);

    private static final String ENTITY_NAME = "clienteRecompensa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClienteRecompensaService clienteRecompensaService;

    public ClienteRecompensaResource(ClienteRecompensaService clienteRecompensaService) {
        this.clienteRecompensaService = clienteRecompensaService;
    }

    /**
     * {@code POST  /cliente-recompensas} : Create a new clienteRecompensa.
     *
     * @param clienteRecompensaDTO the clienteRecompensaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clienteRecompensaDTO, or with status {@code 400 (Bad Request)} if the clienteRecompensa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cliente-recompensas")
    public ResponseEntity<ClienteRecompensaDTO> createClienteRecompensa(@RequestBody ClienteRecompensaDTO clienteRecompensaDTO) throws URISyntaxException {
        log.debug("REST request to save ClienteRecompensa : {}", clienteRecompensaDTO);
        if (clienteRecompensaDTO.getId() != null) {
            throw new BadRequestAlertException("A new clienteRecompensa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteRecompensaDTO result = clienteRecompensaService.save(clienteRecompensaDTO);
        return ResponseEntity.created(new URI("/api/cliente-recompensas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cliente-recompensas} : Updates an existing clienteRecompensa.
     *
     * @param clienteRecompensaDTO the clienteRecompensaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteRecompensaDTO,
     * or with status {@code 400 (Bad Request)} if the clienteRecompensaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clienteRecompensaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cliente-recompensas")
    public ResponseEntity<ClienteRecompensaDTO> updateClienteRecompensa(@RequestBody ClienteRecompensaDTO clienteRecompensaDTO) throws URISyntaxException {
        log.debug("REST request to update ClienteRecompensa : {}", clienteRecompensaDTO);
        if (clienteRecompensaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClienteRecompensaDTO result = clienteRecompensaService.save(clienteRecompensaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteRecompensaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cliente-recompensas} : get all the clienteRecompensas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clienteRecompensas in body.
     */
    @GetMapping("/cliente-recompensas")
    public ResponseEntity<List<ClienteRecompensaDTO>> getAllClienteRecompensas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ClienteRecompensas");
        Page<ClienteRecompensaDTO> page = clienteRecompensaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cliente-recompensas/:id} : get the "id" clienteRecompensa.
     *
     * @param id the id of the clienteRecompensaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clienteRecompensaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cliente-recompensas/{id}")
    public ResponseEntity<ClienteRecompensaDTO> getClienteRecompensa(@PathVariable Long id) {
        log.debug("REST request to get ClienteRecompensa : {}", id);
        Optional<ClienteRecompensaDTO> clienteRecompensaDTO = clienteRecompensaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteRecompensaDTO);
    }

    /**
     * {@code DELETE  /cliente-recompensas/:id} : delete the "id" clienteRecompensa.
     *
     * @param id the id of the clienteRecompensaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cliente-recompensas/{id}")
    public ResponseEntity<Void> deleteClienteRecompensa(@PathVariable Long id) {
        log.debug("REST request to delete ClienteRecompensa : {}", id);
        clienteRecompensaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
