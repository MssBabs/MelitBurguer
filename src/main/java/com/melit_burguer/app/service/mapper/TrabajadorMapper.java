package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.TrabajadorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trabajador} and its DTO {@link TrabajadorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrabajadorMapper extends EntityMapper<TrabajadorDTO, Trabajador> {


    @Mapping(target = "pedidos", ignore = true)
    Trabajador toEntity(TrabajadorDTO trabajadorDTO);

    default Trabajador fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trabajador trabajador = new Trabajador();
        trabajador.setId(id);
        return trabajador;
    }
}
