package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.TipoProductoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.TipoProducto}.
 */
public interface TipoProductoService {

    /**
     * Save a tipoProducto.
     *
     * @param tipoProductoDTO the entity to save.
     * @return the persisted entity.
     */
    TipoProductoDTO save(TipoProductoDTO tipoProductoDTO);

    /**
     * Get all the tipoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoProductoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoProducto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoProductoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoProducto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
