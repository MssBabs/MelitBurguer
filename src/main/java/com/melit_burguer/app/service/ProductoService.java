package com.melit_burguer.app.service;

import com.melit_burguer.app.service.dto.ProductoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.melit_burguer.app.domain.Producto}.
 */
public interface ProductoService {

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoDTO save(ProductoDTO productoDTO);

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" producto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoDTO> findOne(Long id);

    /**
     * Delete the "id" producto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get all the productos By Type.
     *
     * @param productoType the id of the entity.
     * @return the list of entities.
     */
    Page<ProductoDTO> getProductosByType(long tipoProductoId, Pageable pageable);

    // Page<ProductoDTO> getProductosByName(Pageable pageable);
}
