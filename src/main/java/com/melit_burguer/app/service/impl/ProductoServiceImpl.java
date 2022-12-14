package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.ProductoService;
import com.melit_burguer.app.domain.Producto;
import com.melit_burguer.app.repository.ProductoRepository;
import com.melit_burguer.app.service.dto.ProductoDTO;
import com.melit_burguer.app.service.mapper.ProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        return productoRepository.findAll(pageable)
            .map(productoMapper::toDto);
    }

// /**
//      * Get all the productos por nombre para el modal
//      *
//      * @param pageable the pagination information.
//      * @return the list of entities.
//      */
//     @Override
//     @Transactional(readOnly = true)
//     public Page<ProductoDTO> getProductosByName(Pageable pageable) {
//         log.debug("Request to get all Productos");
//         return productoRepository.getProductosName(pageable)
//             .map(productoMapper::toDto);
//     }



    /**
     * Get one producto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findById(id)
            .map(productoMapper::toDto);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
    }

    /**
     * Get all the productos By Type.
     *
     * @param productoType the id of the entity.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> getProductosByType(long tipoProductoId, Pageable pageable){
        log.debug("Request to get Producto : {}", tipoProductoId);
        return productoRepository.getProductosByType(tipoProductoId, pageable)
            .map(productoMapper::toDto);
    }
}
