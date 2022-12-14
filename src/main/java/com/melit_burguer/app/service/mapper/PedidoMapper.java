package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.PedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EstadoPedidoMapper.class, ClienteMapper.class, TrabajadorMapper.class, ProductosPedidoMapper.class })
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {

    @Mapping(source = "estadoPedido.id", target = "estadoPedidoId")
    @Mapping(source = "estadoPedido.nombre", target = "estadoPedidoNombre")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    @Mapping(source = "cliente.apellido", target = "clienteApellido")
    @Mapping(source = "trabajador.id", target = "trabajadorId")
    @Mapping(source = "trabajador.nombre", target = "trabajadorNombre")
    @Mapping(source = "trabajador.apellido", target = "trabajadorApellido")
    PedidoDTO toDto(Pedido pedido);

    @Mapping(source = "estadoPedidoId", target = "estadoPedido")
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "trabajadorId", target = "trabajador")
    Pedido toEntity(PedidoDTO pedidoDTO);

    default Pedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setId(id);
        return pedido;
    }

    /**
     * @AfterMapping // or @BeforeMapping
     * default void calculateTotal(Pedido pedido, @MappingTarget PedidoDTO dto) {
     *  dto.setProductosPedidosPrecioTotal(pedido.getProductosPedidos().stream().mapToDouble(ProductosPedido::getPrecioTotal).sum());
     * }
     */
}
