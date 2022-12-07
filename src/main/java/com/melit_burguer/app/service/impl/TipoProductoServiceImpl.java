package com.melit_burguer.app.service.impl;

import com.melit_burguer.app.service.TipoProductoService;
import com.melit_burguer.app.domain.TipoProducto;
import com.melit_burguer.app.repository.TipoProductoRepository;
import com.melit_burguer.app.service.dto.TipoProductoDTO;
import com.melit_burguer.app.service.mapper.TipoProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoProducto}.
 */
@Service
@Transactional
public class TipoProductoServiceImpl implements TipoProductoService {

    private final Logger log = LoggerFactory.getLogger(TipoProductoServiceImpl.class);

    private final TipoProductoRepository tipoProductoRepository;

    private final TipoProductoMapper tipoProductoMapper;

    public TipoProductoServiceImpl(TipoProductoRepository tipoProductoRepository, TipoProductoMapper tipoProductoMapper) {
        this.tipoProductoRepository = tipoProductoRepository;
        this.tipoProductoMapper = tipoProductoMapper;
    }

    /**
     * Save a tipoProducto.
     *
     * @param tipoProductoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoProductoDTO save(TipoProductoDTO tipoProductoDTO) {
        log.debug("Request to save TipoProducto : {}", tipoProductoDTO);
        TipoProducto tipoProducto = tipoProductoMapper.toEntity(tipoProductoDTO);
        tipoProducto = tipoProductoRepository.save(tipoProducto);
        return tipoProductoMapper.toDto(tipoProducto);
    }

    /**
     * Get all the tipoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoProductos");
        return tipoProductoRepository.findAll(pageable)
            .map(tipoProductoMapper::toDto);
    }


    /**
     * Get one tipoProducto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoProductoDTO> findOne(Long id) {
        log.debug("Request to get TipoProducto : {}", id);
        return tipoProductoRepository.findById(id)
            .map(tipoProductoMapper::toDto);
    }

    /**
     * Delete the tipoProducto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoProducto : {}", id);
        tipoProductoRepository.deleteById(id);
    }
}
