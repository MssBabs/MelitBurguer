package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.ClienteRecompensaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.ClienteRecompensa}.
 */
public interface ClienteRecompensaService {

    /**
     * Save a clienteRecompensa.
     *
     * @param clienteRecompensaDTO the entity to save.
     * @return the persisted entity.
     */
    ClienteRecompensaDTO save(ClienteRecompensaDTO clienteRecompensaDTO);

    /**
     * Get all the clienteRecompensas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClienteRecompensaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clienteRecompensa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClienteRecompensaDTO> findOne(Long id);

    /**
     * Delete the "id" clienteRecompensa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
