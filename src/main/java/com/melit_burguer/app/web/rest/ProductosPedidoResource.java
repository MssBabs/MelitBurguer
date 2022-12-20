package com.melit_burguer.app.web.rest;

import com.melit_burguer.app.service.ProductosPedidoService;
import com.melit_burguer.app.web.rest.errors.BadRequestAlertException;
import com.melit_burguer.app.service.dto.ProductosPedidoDTO;

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
 * REST controller for managing {@link com.melit_burguer.app.domain.ProductosPedido}.
 */
@RestController
@RequestMapping("/api")
public class ProductosPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ProductosPedidoResource.class);

    private static final String ENTITY_NAME = "productosPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductosPedidoService productosPedidoService;

    public ProductosPedidoResource(ProductosPedidoService productosPedidoService) {
        this.productosPedidoService = productosPedidoService;
    }

    /**
     * {@code POST  /productos-pedidos} : Create a new productosPedido.
     *
     * @param productosPedidoDTO the productosPedidoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productosPedidoDTO, or with status {@code 400 (Bad Request)} if the productosPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/productos-pedidos")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductosPedidoDTO> createProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO) throws URISyntaxException {
        log.debug("REST request to save ProductosPedido : {}", productosPedidoDTO);
        if (productosPedidoDTO.getId() != null) {
            throw new BadRequestAlertException("A new productosPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductosPedidoDTO result = productosPedidoService.save(productosPedidoDTO);
        return ResponseEntity.created(new URI("/api/productos-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /productos-pedidos} : Updates an existing productosPedido.
     *
     * @param productosPedidoDTO the productosPedidoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productosPedidoDTO,
     * or with status {@code 400 (Bad Request)} if the productosPedidoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productosPedidoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/productos-pedidos")
    @Timed
    @PreAuthorize("hasRole('ROLE_TRABAJADOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductosPedidoDTO> updateProductosPedido(@RequestBody ProductosPedidoDTO productosPedidoDTO) throws URISyntaxException {
        log.debug("REST request to update ProductosPedido : {}", productosPedidoDTO);
        if (productosPedidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductosPedidoDTO result = productosPedidoService.save(productosPedidoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productosPedidoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /productos-pedidos} : get all the productosPedidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productosPedidos in body.
     */
    @GetMapping("/productos-pedidos")
        @Timed
        @PreAuthorize("hasRole('ROLE_TRABAJADOR') or hasRole('ROLE_TRABAJADOR_COCINA') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductosPedidoDTO>> getAllProductosPedidos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ProductosPedidos");
        Page<ProductosPedidoDTO> page =null;
        if(queryParams.containsKey("pedidoId")){
            page = productosPedidoService.findAllByPedidoId(queryParams.get("pedidoId"));
        }else{
            page = productosPedidoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /productos-pedidos/:id} : get the "id" productosPedido.
     *
     * @param id the id of the productosPedidoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productosPedidoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productos-pedidos/{id}")
        @Timed
        @PreAuthorize("hasRole('ROLE_TRABAJADOR') or hasRole('ROLE_TRABAJADOR_COCINA') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductosPedidoDTO> getProductosPedido(@PathVariable Long id) {
        log.debug("REST request to get ProductosPedido : {}", id);
        Optional<ProductosPedidoDTO> productosPedidoDTO = productosPedidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productosPedidoDTO);
    }

    /**
     * {@code DELETE  /productos-pedidos/:id} : delete the "id" productosPedido.
     *
     * @param id the id of the productosPedidoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productos-pedidos/{id}")
        @Timed
        @PreAuthorize("hasRole('ROLE_TRABAJADOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProductosPedido(@PathVariable Long id) {
        log.debug("REST request to delete ProductosPedido : {}", id);
        productosPedidoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
