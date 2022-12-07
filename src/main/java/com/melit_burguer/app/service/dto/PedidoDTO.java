package com.melit_burguer.app.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.melit_burguer.app.domain.Pedido} entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    private Double precioFinal;

    private LocalDate fecha;


    private Long estadoPedidoId;

    private Long clienteId;

    private Long trabajadorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getEstadoPedidoId() {
        return estadoPedidoId;
    }

    public void setEstadoPedidoId(Long estadoPedidoId) {
        this.estadoPedidoId = estadoPedidoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", precioFinal=" + getPrecioFinal() +
            ", fecha='" + getFecha() + "'" +
            ", estadoPedido=" + getEstadoPedidoId() +
            ", cliente=" + getClienteId() +
            ", trabajador=" + getTrabajadorId() +
            "}";
    }
}
