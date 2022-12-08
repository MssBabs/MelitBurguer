package com.melit_burguer.app.service.mapper;

import com.melit_burguer.app.domain.*;
import com.melit_burguer.app.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoProductoMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {
    /*tipoProductoId -> Ahora muestra el tipoProductoNombre*/
    @Mapping(source = "tipoProducto.nombre", target = "tipoProductoId")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "tipoProductoId", target = "tipoProducto")
    @Mapping(target = "productosPedidos", ignore = true)
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
