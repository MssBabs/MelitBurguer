package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.EstadoPedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadoPedido} and its DTO {@link EstadoPedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstadoPedidoMapper extends EntityMapper<EstadoPedidoDTO, EstadoPedido> {


    //@Mapping(target = "pedido", ignore = true)
    EstadoPedido toEntity(EstadoPedidoDTO estadoPedidoDTO);

    default EstadoPedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstadoPedido estadoPedido = new EstadoPedido();
        estadoPedido.setId(id);
        return estadoPedido;
    }
}
