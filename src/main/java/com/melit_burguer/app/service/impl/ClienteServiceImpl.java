package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.ClienteService;
import com.melit_burguer.app.domain.Cliente;
import com.melit_burguer.app.repository.ClienteRepository;
import com.melit_burguer.app.service.dto.ClienteDTO;
import com.melit_burguer.app.service.mapper.ClienteMapper;
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
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable)
            .map(clienteMapper::toDto);
    }



    /**
    *  Get all the clientes where Pedido is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAllWherePedidoIsNull() {
        log.debug("Request to get all clientes where Pedido is null");
        return StreamSupport
            .stream(clienteRepository.findAll().spliterator(), false)
            //.filter(cliente -> cliente.getPedido() == null)
            .map(clienteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the clientes where ClienteRecompensa is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> findAllWhereClienteRecompensaIsNull() {
        log.debug("Request to get all clientes where ClienteRecompensa is null");
        return StreamSupport
            .stream(clienteRepository.findAll().spliterator(), false)
            .filter(cliente -> cliente.getClienteRecompensa() == null)
            .map(clienteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id)
            .map(clienteMapper::toDto);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }
}
