package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.PedidoDTO;
import com.melit_burguer.app.service.dto.ProductoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.Pedido}.
 */
public interface PedidoService {

    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save.
     * @return the persisted entity.
     */
    PedidoDTO save(PedidoDTO pedidoDTO);

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PedidoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PedidoDTO> findOne(Long id);

    /**
     * Delete the "id" pedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    // Page<ProductoDTO> findAllProductos(Pageable pageable);
}
