package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.EstadoPedidoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.EstadoPedido}.
 */
public interface EstadoPedidoService {

    /**
     * Save a estadoPedido.
     *
     * @param estadoPedidoDTO the entity to save.
     * @return the persisted entity.
     */
    EstadoPedidoDTO save(EstadoPedidoDTO estadoPedidoDTO);

    /**
     * Get all the estadoPedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoPedidoDTO> findAll(Pageable pageable);
    /**
     * Get all the EstadoPedidoDTO where Pedido is {@code null}.
     *
     * @return the list of entities.
     */
    List<EstadoPedidoDTO> findAllWherePedidoIsNull();


    /**
     * Get the "id" estadoPedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoPedidoDTO> findOne(Long id);

    /**
     * Delete the "id" estadoPedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
