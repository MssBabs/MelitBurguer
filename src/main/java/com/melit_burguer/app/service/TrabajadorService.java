package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.TrabajadorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.Trabajador}.
 */
public interface TrabajadorService {

    /**
     * Save a trabajador.
     *
     * @param trabajadorDTO the entity to save.
     * @return the persisted entity.
     */
    TrabajadorDTO save(TrabajadorDTO trabajadorDTO);

    /**
     * Get all the trabajadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrabajadorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" trabajador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrabajadorDTO> findOne(Long id);

    /**
     * Delete the "id" trabajador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
