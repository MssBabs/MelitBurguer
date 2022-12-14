package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.ProductosPedidoService;
import com.melit_burguer.app.domain.ProductosPedido;
import com.melit_burguer.app.repository.ProductosPedidoRepository;
import com.melit_burguer.app.service.dto.ProductosPedidoDTO;
import com.melit_burguer.app.service.mapper.ProductosPedidoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProductosPedido}.
 */
@Service
@Transactional
public class ProductosPedidoServiceImpl implements ProductosPedidoService {

    private final Logger log = LoggerFactory.getLogger(ProductosPedidoServiceImpl.class);

    private final ProductosPedidoRepository productosPedidoRepository;

    private final ProductosPedidoMapper productosPedidoMapper;

    public ProductosPedidoServiceImpl(ProductosPedidoRepository productosPedidoRepository, ProductosPedidoMapper productosPedidoMapper) {
        this.productosPedidoRepository = productosPedidoRepository;
        this.productosPedidoMapper = productosPedidoMapper;
    }

    /**
     * Save a productosPedido.
     *
     * @param productosPedidoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductosPedidoDTO save(ProductosPedidoDTO productosPedidoDTO) {
        log.debug("Request to save ProductosPedido : {}", productosPedidoDTO);
        ProductosPedido productosPedido = productosPedidoMapper.toEntity(productosPedidoDTO);
        productosPedido = productosPedidoRepository.save(productosPedido);
        return productosPedidoMapper.toDto(productosPedido);
    }

    /**
     * Get all the productosPedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */


    /**
     * Get one productosPedido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductosPedidoDTO> findOne(Long id) {
        log.debug("Request to get ProductosPedido : {}", id);
        return productosPedidoRepository.findById(id)
            .map(productosPedidoMapper::toDto);
    }

    /**
     * Delete the productosPedido by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductosPedido : {}", id);
        productosPedidoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductosPedidoDTO> findAllByPedidoId(List<String> list) {

        List<Long> longs=list.stream().map(Long::parseLong).collect(Collectors.toList());
        return productosPedidoRepository.findAllByPedidoId(longs,null).map(productosPedidoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductosPedidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductosPedidos");
        return productosPedidoRepository.findAll(pageable)
            .map(productosPedidoMapper::toDto);
    }

}
