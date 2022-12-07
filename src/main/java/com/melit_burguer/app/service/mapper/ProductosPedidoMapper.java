package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.ProductosPedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductosPedido} and its DTO {@link ProductosPedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, PedidoMapper.class})
public interface ProductosPedidoMapper extends EntityMapper<ProductosPedidoDTO, ProductosPedido> {

    @Mapping(source = "productos.id", target = "productosId")
    @Mapping(source = "pedido.id", target = "pedidoId")
    ProductosPedidoDTO toDto(ProductosPedido productosPedido);

    @Mapping(source = "productosId", target = "productos")
    @Mapping(source = "pedidoId", target = "pedido")
    ProductosPedido toEntity(ProductosPedidoDTO productosPedidoDTO);

    default ProductosPedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductosPedido productosPedido = new ProductosPedido();
        productosPedido.setId(id);
        return productosPedido;
    }
}
