package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.service.RecompensaService;
import com.melit_burguer.app.web.rest.errors.BadRequestAlertException;
import com.melit_burguer.app.service.dto.RecompensaDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.melit_burguer.app.domain.Recompensa}.
 */
@RestController
@RequestMapping("/api")
public class RecompensaResource {

    private final Logger log = LoggerFactory.getLogger(RecompensaResource.class);

    private static final String ENTITY_NAME = "recompensa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecompensaService recompensaService;

    public RecompensaResource(RecompensaService recompensaService) {
        this.recompensaService = recompensaService;
    }

    /**
     * {@code POST  /recompensas} : Create a new recompensa.
     *
     * @param recompensaDTO the recompensaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recompensaDTO, or with status {@code 400 (Bad Request)} if the recompensa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recompensas")
    public ResponseEntity<RecompensaDTO> createRecompensa(@RequestBody RecompensaDTO recompensaDTO) throws URISyntaxException {
        log.debug("REST request to save Recompensa : {}", recompensaDTO);
        if (recompensaDTO.getId() != null) {
            throw new BadRequestAlertException("A new recompensa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecompensaDTO result = recompensaService.save(recompensaDTO);
        return ResponseEntity.created(new URI("/api/recompensas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recompensas} : Updates an existing recompensa.
     *
     * @param recompensaDTO the recompensaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recompensaDTO,
     * or with status {@code 400 (Bad Request)} if the recompensaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recompensaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recompensas")
    public ResponseEntity<RecompensaDTO> updateRecompensa(@RequestBody RecompensaDTO recompensaDTO) throws URISyntaxException {
        log.debug("REST request to update Recompensa : {}", recompensaDTO);
        if (recompensaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecompensaDTO result = recompensaService.save(recompensaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recompensaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recompensas} : get all the recompensas.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recompensas in body.
     */
    @GetMapping("/recompensas")
    public ResponseEntity<List<RecompensaDTO>> getAllRecompensas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false) String filter) {
        if ("clienterecompensa-is-null".equals(filter)) {
            log.debug("REST request to get all Recompensas where clienteRecompensa is null");
            return new ResponseEntity<>(recompensaService.findAllWhereClienteRecompensaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Recompensas");
        Page<RecompensaDTO> page = recompensaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recompensas/:id} : get the "id" recompensa.
     *
     * @param id the id of the recompensaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recompensaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recompensas/{id}")
    public ResponseEntity<RecompensaDTO> getRecompensa(@PathVariable Long id) {
        log.debug("REST request to get Recompensa : {}", id);
        Optional<RecompensaDTO> recompensaDTO = recompensaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recompensaDTO);
    }

    /**
     * {@code DELETE  /recompensas/:id} : delete the "id" recompensa.
     *
     * @param id the id of the recompensaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recompensas/{id}")
    public ResponseEntity<Void> deleteRecompensa(@PathVariable Long id) {
        log.debug("REST request to delete Recompensa : {}", id);
        recompensaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
