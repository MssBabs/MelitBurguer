package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.service.TipoProductoService;
import com.melit_burguer.app.web.rest.errors.BadRequestAlertException;
import com.melit_burguer.app.service.dto.TipoProductoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.melit_burguer.app.domain.TipoProducto}.
 */
@RestController
@RequestMapping("/api")
public class TipoProductoResource {

    private final Logger log = LoggerFactory.getLogger(TipoProductoResource.class);

    private static final String ENTITY_NAME = "tipoProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoProductoService tipoProductoService;

    public TipoProductoResource(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    /**
     * {@code POST  /tipo-productos} : Create a new tipoProducto.
     *
     * @param tipoProductoDTO the tipoProductoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoProductoDTO, or with status {@code 400 (Bad Request)} if the tipoProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-productos")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TipoProductoDTO> createTipoProducto(@RequestBody TipoProductoDTO tipoProductoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoProducto : {}", tipoProductoDTO);
        if (tipoProductoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoProductoDTO result = tipoProductoService.save(tipoProductoDTO);
        return ResponseEntity.created(new URI("/api/tipo-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-productos} : Updates an existing tipoProducto.
     *
     * @param tipoProductoDTO the tipoProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProductoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoProductoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-productos")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TipoProductoDTO> updateTipoProducto(@RequestBody TipoProductoDTO tipoProductoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoProducto : {}", tipoProductoDTO);
        if (tipoProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoProductoDTO result = tipoProductoService.save(tipoProductoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProductoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-productos} : get all the tipoProductos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoProductos in body.
     */
    @GetMapping("/tipo-productos")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_TRABAJADOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TipoProductoDTO>> getAllTipoProductos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TipoProductos");
        Page<TipoProductoDTO> page = tipoProductoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-productos/:id} : get the "id" tipoProducto.
     *
     * @param id the id of the tipoProductoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoProductoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-productos/{id}")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_TRABAJADOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TipoProductoDTO> getTipoProducto(@PathVariable Long id) {
        log.debug("REST request to get TipoProducto : {}", id);
        Optional<TipoProductoDTO> tipoProductoDTO = tipoProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoProductoDTO);
    }

    /**
     * {@code DELETE  /tipo-productos/:id} : delete the "id" tipoProducto.
     *
     * @param id the id of the tipoProductoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-productos/{id}")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTipoProducto(@PathVariable Long id) {
        log.debug("REST request to delete TipoProducto : {}", id);
        tipoProductoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
