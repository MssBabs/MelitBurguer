package com.melit_burguer.app.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.melit_burguer.app.domain.ProductosPedido} entity.
 */
public class ProductosPedidoDTO implements Serializable {

    private Long id;

    private Double precioTotal;


    private Long productosId;

    private Long pedidoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Long getProductosId() {
        return productosId;
    }

    public void setProductosId(Long productoId) {
        this.productosId = productoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductosPedidoDTO productosPedidoDTO = (ProductosPedidoDTO) o;
        if (productosPedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productosPedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductosPedidoDTO{" +
            "id=" + getId() +
            ", precioTotal=" + getPrecioTotal() +
            ", productos=" + getProductosId() +
            ", pedido=" + getPedidoId() +
            "}";
    }
}
