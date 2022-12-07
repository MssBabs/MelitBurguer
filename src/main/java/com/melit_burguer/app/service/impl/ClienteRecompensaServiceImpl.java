package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.ClienteRecompensaService;
import com.melit_burguer.app.domain.ClienteRecompensa;
import com.melit_burguer.app.repository.ClienteRecompensaRepository;
import com.melit_burguer.app.service.dto.ClienteRecompensaDTO;
import com.melit_burguer.app.service.mapper.ClienteRecompensaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClienteRecompensa}.
 */
@Service
@Transactional
public class ClienteRecompensaServiceImpl implements ClienteRecompensaService {

    private final Logger log = LoggerFactory.getLogger(ClienteRecompensaServiceImpl.class);

    private final ClienteRecompensaRepository clienteRecompensaRepository;

    private final ClienteRecompensaMapper clienteRecompensaMapper;

    public ClienteRecompensaServiceImpl(ClienteRecompensaRepository clienteRecompensaRepository, ClienteRecompensaMapper clienteRecompensaMapper) {
        this.clienteRecompensaRepository = clienteRecompensaRepository;
        this.clienteRecompensaMapper = clienteRecompensaMapper;
    }

    /**
     * Save a clienteRecompensa.
     *
     * @param clienteRecompensaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClienteRecompensaDTO save(ClienteRecompensaDTO clienteRecompensaDTO) {
        log.debug("Request to save ClienteRecompensa : {}", clienteRecompensaDTO);
        ClienteRecompensa clienteRecompensa = clienteRecompensaMapper.toEntity(clienteRecompensaDTO);
        clienteRecompensa = clienteRecompensaRepository.save(clienteRecompensa);
        return clienteRecompensaMapper.toDto(clienteRecompensa);
    }

    /**
     * Get all the clienteRecompensas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteRecompensaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteRecompensas");
        return clienteRecompensaRepository.findAll(pageable)
            .map(clienteRecompensaMapper::toDto);
    }


    /**
     * Get one clienteRecompensa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteRecompensaDTO> findOne(Long id) {
        log.debug("Request to get ClienteRecompensa : {}", id);
        return clienteRecompensaRepository.findById(id)
            .map(clienteRecompensaMapper::toDto);
    }

    /**
     * Delete the clienteRecompensa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClienteRecompensa : {}", id);
        clienteRecompensaRepository.deleteById(id);
    }
}
