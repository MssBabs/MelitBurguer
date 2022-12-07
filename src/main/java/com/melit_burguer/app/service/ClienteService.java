package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.ClienteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.Cliente}.
 */
public interface ClienteService {

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    ClienteDTO save(ClienteDTO clienteDTO);

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClienteDTO> findAll(Pageable pageable);
    /**
     * Get all the ClienteDTO where Pedido is {@code null}.
     *
     * @return the list of entities.
     */
    List<ClienteDTO> findAllWherePedidoIsNull();
    /**
     * Get all the ClienteDTO where ClienteRecompensa is {@code null}.
     *
     * @return the list of entities.
     */
    List<ClienteDTO> findAllWhereClienteRecompensaIsNull();


    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClienteDTO> findOne(Long id);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
