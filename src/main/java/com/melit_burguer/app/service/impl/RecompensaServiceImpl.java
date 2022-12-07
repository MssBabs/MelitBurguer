package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.RecompensaService;
import com.melit_burguer.app.domain.Recompensa;
import com.melit_burguer.app.repository.RecompensaRepository;
import com.melit_burguer.app.service.dto.RecompensaDTO;
import com.melit_burguer.app.service.mapper.RecompensaMapper;
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
 * Service Implementation for managing {@link Recompensa}.
 */
@Service
@Transactional
public class RecompensaServiceImpl implements RecompensaService {

    private final Logger log = LoggerFactory.getLogger(RecompensaServiceImpl.class);

    private final RecompensaRepository recompensaRepository;

    private final RecompensaMapper recompensaMapper;

    public RecompensaServiceImpl(RecompensaRepository recompensaRepository, RecompensaMapper recompensaMapper) {
        this.recompensaRepository = recompensaRepository;
        this.recompensaMapper = recompensaMapper;
    }

    /**
     * Save a recompensa.
     *
     * @param recompensaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RecompensaDTO save(RecompensaDTO recompensaDTO) {
        log.debug("Request to save Recompensa : {}", recompensaDTO);
        Recompensa recompensa = recompensaMapper.toEntity(recompensaDTO);
        recompensa = recompensaRepository.save(recompensa);
        return recompensaMapper.toDto(recompensa);
    }

    /**
     * Get all the recompensas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecompensaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recompensas");
        return recompensaRepository.findAll(pageable)
            .map(recompensaMapper::toDto);
    }



    /**
    *  Get all the recompensas where ClienteRecompensa is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<RecompensaDTO> findAllWhereClienteRecompensaIsNull() {
        log.debug("Request to get all recompensas where ClienteRecompensa is null");
        return StreamSupport
            .stream(recompensaRepository.findAll().spliterator(), false)
            .filter(recompensa -> recompensa.getClienteRecompensa() == null)
            .map(recompensaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one recompensa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecompensaDTO> findOne(Long id) {
        log.debug("Request to get Recompensa : {}", id);
        return recompensaRepository.findById(id)
            .map(recompensaMapper::toDto);
    }

    /**
     * Delete the recompensa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recompensa : {}", id);
        recompensaRepository.deleteById(id);
    }
}
