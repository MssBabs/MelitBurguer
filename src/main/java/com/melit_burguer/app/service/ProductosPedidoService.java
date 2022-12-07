package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.ProductosPedidoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.ProductosPedido}.
 */
public interface ProductosPedidoService {

    /**
     * Save a productosPedido.
     *
     * @param productosPedidoDTO the entity to save.
     * @return the persisted entity.
     */
    ProductosPedidoDTO save(ProductosPedidoDTO productosPedidoDTO);

    /**
     * Get all the productosPedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductosPedidoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productosPedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductosPedidoDTO> findOne(Long id);

    /**
     * Delete the "id" productosPedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
