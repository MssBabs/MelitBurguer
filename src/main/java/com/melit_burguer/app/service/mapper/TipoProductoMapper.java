package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.TipoProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoProducto} and its DTO {@link TipoProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoProductoMapper extends EntityMapper<TipoProductoDTO, TipoProducto> {


    @Mapping(target = "productos", ignore = true)
    TipoProducto toEntity(TipoProductoDTO tipoProductoDTO);

    default TipoProducto fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setId(id);
        return tipoProducto;
    }
}
