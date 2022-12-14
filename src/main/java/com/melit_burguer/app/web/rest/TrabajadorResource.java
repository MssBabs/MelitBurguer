package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.service.TrabajadorService;
import com.melit_burguer.app.web.rest.errors.BadRequestAlertException;
import com.melit_burguer.app.service.dto.TrabajadorDTO;

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
 * REST controller for managing {@link com.melit_burguer.app.domain.Trabajador}.
 */
@RestController
@RequestMapping("/api")
public class TrabajadorResource {

    private final Logger log = LoggerFactory.getLogger(TrabajadorResource.class);

    private static final String ENTITY_NAME = "trabajador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrabajadorService trabajadorService;

    public TrabajadorResource(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    /**
     * {@code POST  /trabajadors} : Create a new trabajador.
     *
     * @param trabajadorDTO the trabajadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trabajadorDTO, or with status {@code 400 (Bad Request)} if the trabajador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trabajadors")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrabajadorDTO> createTrabajador(@RequestBody TrabajadorDTO trabajadorDTO) throws URISyntaxException {
        log.debug("REST request to save Trabajador : {}", trabajadorDTO);
        if (trabajadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new trabajador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrabajadorDTO result = trabajadorService.save(trabajadorDTO);
        return ResponseEntity.created(new URI("/api/trabajadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trabajadors} : Updates an existing trabajador.
     *
     * @param trabajadorDTO the trabajadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trabajadorDTO,
     * or with status {@code 400 (Bad Request)} if the trabajadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trabajadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trabajadors")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrabajadorDTO> updateTrabajador(@RequestBody TrabajadorDTO trabajadorDTO) throws URISyntaxException {
        log.debug("REST request to update Trabajador : {}", trabajadorDTO);
        if (trabajadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrabajadorDTO result = trabajadorService.save(trabajadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trabajadorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trabajadors} : get all the trabajadors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trabajadors in body.
     */
    @GetMapping("/trabajadors")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TrabajadorDTO>> getAllTrabajadors(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Trabajadors");
        Page<TrabajadorDTO> page = trabajadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trabajadors/:id} : get the "id" trabajador.
     *
     * @param id the id of the trabajadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trabajadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trabajadors/{id}")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrabajadorDTO> getTrabajador(@PathVariable Long id) {
        log.debug("REST request to get Trabajador : {}", id);
        Optional<TrabajadorDTO> trabajadorDTO = trabajadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trabajadorDTO);
    }

    /**
     * {@code DELETE  /trabajadors/:id} : delete the "id" trabajador.
     *
     * @param id the id of the trabajadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trabajadors/{id}")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR_JEFE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Long id) {
        log.debug("REST request to delete Trabajador : {}", id);
        trabajadorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
