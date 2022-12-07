package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.EstadoPedidoService;
import com.melit_burguer.app.domain.EstadoPedido;
import com.melit_burguer.app.repository.EstadoPedidoRepository;
import com.melit_burguer.app.service.dto.EstadoPedidoDTO;
import com.melit_burguer.app.service.mapper.EstadoPedidoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link EstadoPedido}.
 */
@Service
@Transactional
public class EstadoPedidoServiceImpl implements EstadoPedidoService {

    private final Logger log = LoggerFactory.getLogger(EstadoPedidoServiceImpl.class);

    private final EstadoPedidoRepository estadoPedidoRepository;

    private final EstadoPedidoMapper estadoPedidoMapper;

    public EstadoPedidoServiceImpl(EstadoPedidoRepository estadoPedidoRepository, EstadoPedidoMapper estadoPedidoMapper) {
        this.estadoPedidoRepository = estadoPedidoRepository;
        this.estadoPedidoMapper = estadoPedidoMapper;
    }

    /**
     * Save a estadoPedido.
     *
     * @param estadoPedidoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EstadoPedidoDTO save(EstadoPedidoDTO estadoPedidoDTO) {
        log.debug("Request to save EstadoPedido : {}", estadoPedidoDTO);
        EstadoPedido estadoPedido = estadoPedidoMapper.toEntity(estadoPedidoDTO);
        estadoPedido = estadoPedidoRepository.save(estadoPedido);
        return estadoPedidoMapper.toDto(estadoPedido);
    }

    /**
     * Get all the estadoPedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstadoPedidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoPedidos");
        return estadoPedidoRepository.findAll(pageable)
            .map(estadoPedidoMapper::toDto);
    }



    /**
    *  Get all the estadoPedidos where Pedido is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<EstadoPedidoDTO> findAllWherePedidoIsNull() {
        log.debug("Request to get all estadoPedidos where Pedido is null");
        return StreamSupport
            .stream(estadoPedidoRepository.findAll().spliterator(), false)
            .filter(estadoPedido -> estadoPedido.getPedido() == null)
            .map(estadoPedidoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one estadoPedido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoPedidoDTO> findOne(Long id) {
        log.debug("Request to get EstadoPedido : {}", id);
        return estadoPedidoRepository.findById(id)
            .map(estadoPedidoMapper::toDto);
    }

    /**
     * Delete the estadoPedido by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoPedido : {}", id);
        estadoPedidoRepository.deleteById(id);
    }
}
