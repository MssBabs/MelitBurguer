package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.PedidoService;
import com.melit_burguer.app.service.ProductosPedidoService;
import com.melit_burguer.app.domain.Pedido;
import com.melit_burguer.app.domain.ProductosPedido;
import com.melit_burguer.app.repository.PedidoRepository;
import com.melit_burguer.app.service.dto.PedidoDTO;
import com.melit_burguer.app.service.mapper.PedidoMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pedido}.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    private final PedidoRepository pedidoRepository;
    private final ProductosPedidoService productosPedidoService;

    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, ProductosPedidoService productosPedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.productosPedidoService=productosPedidoService;
    }

    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PedidoDTO save(PedidoDTO pedidoDTO) {
        log.debug("Request to save Pedido : {}", pedidoDTO);
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        if(pedido.getEstadoPedido().getId() ==3){
          Optional<Pedido> pedidoFinal = pedidoRepository.findById(pedido.getId());
          if(pedidoFinal.isPresent()){
            Optional<Double> a = pedidoFinal
            .get()
            .getProductosPedidos()
            .stream()
            .map(ProductosPedido::getPrecio)
            .collect(Collectors.toList())
            .stream().reduce(Double::sum);
            if(a.isPresent()){
                pedido.setPrecioFinal(Math.floor(a.get()*1.21));
            }
          }
        }
        pedido.setFecha(LocalDate.now().plusDays(1));
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pedidos");
        return pedidoRepository.findAll(pageable)
            .map(pedidoMapper::toDto);
    }


    /**
     * Get one pedido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoDTO> findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findById(id)
            .map(pedidoMapper::toDto);
    }

    /**
     * Delete the pedido by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
    }
}
