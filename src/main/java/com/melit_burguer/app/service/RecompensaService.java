package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.RecompensaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.Recompensa}.
 */
public interface RecompensaService {

    /**
     * Save a recompensa.
     *
     * @param recompensaDTO the entity to save.
     * @return the persisted entity.
     */
    RecompensaDTO save(RecompensaDTO recompensaDTO);

    /**
     * Get all the recompensas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecompensaDTO> findAll(Pageable pageable);
    /**
     * Get all the RecompensaDTO where ClienteRecompensa is {@code null}.
     *
     * @return the list of entities.
     */
    List<RecompensaDTO> findAllWhereClienteRecompensaIsNull();


    /**
     * Get the "id" recompensa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecompensaDTO> findOne(Long id);

    /**
     * Delete the "id" recompensa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
