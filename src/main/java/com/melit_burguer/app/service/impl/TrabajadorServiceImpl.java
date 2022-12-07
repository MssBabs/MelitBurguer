package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.TrabajadorService;
import com.melit_burguer.app.domain.Trabajador;
import com.melit_burguer.app.repository.TrabajadorRepository;
import com.melit_burguer.app.service.dto.TrabajadorDTO;
import com.melit_burguer.app.service.mapper.TrabajadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Trabajador}.
 */
@Service
@Transactional
public class TrabajadorServiceImpl implements TrabajadorService {

    private final Logger log = LoggerFactory.getLogger(TrabajadorServiceImpl.class);

    private final TrabajadorRepository trabajadorRepository;

    private final TrabajadorMapper trabajadorMapper;

    public TrabajadorServiceImpl(TrabajadorRepository trabajadorRepository, TrabajadorMapper trabajadorMapper) {
        this.trabajadorRepository = trabajadorRepository;
        this.trabajadorMapper = trabajadorMapper;
    }

    /**
     * Save a trabajador.
     *
     * @param trabajadorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrabajadorDTO save(TrabajadorDTO trabajadorDTO) {
        log.debug("Request to save Trabajador : {}", trabajadorDTO);
        Trabajador trabajador = trabajadorMapper.toEntity(trabajadorDTO);
        trabajador = trabajadorRepository.save(trabajador);
        return trabajadorMapper.toDto(trabajador);
    }

    /**
     * Get all the trabajadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrabajadorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trabajadors");
        return trabajadorRepository.findAll(pageable)
            .map(trabajadorMapper::toDto);
    }


    /**
     * Get one trabajador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrabajadorDTO> findOne(Long id) {
        log.debug("Request to get Trabajador : {}", id);
        return trabajadorRepository.findById(id)
            .map(trabajadorMapper::toDto);
    }

    /**
     * Delete the trabajador by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trabajador : {}", id);
        trabajadorRepository.deleteById(id);
    }
}
